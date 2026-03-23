package com.secure.resident.main.presentation.viewmodel.chat.getGroupMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.repository.ChatSocketManager
import com.secure.resident.main.domain.usecase.chat.getGroupMessage.GetGroupMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetGroupMessageViewModel @Inject constructor(
    private val getGroupMessageUseCase: GetGroupMessageUseCase ,
    private val chatSocketManager: ChatSocketManager
) : ViewModel() {

    private val _getGroupMessagesState = MutableStateFlow<ResultState<List<Message>>>(ResultState.Idle)
    val getGroupMessageState : StateFlow<ResultState<List<Message>>> = _getGroupMessagesState.asStateFlow()

    fun getGroupMessages(
        groupId : String ,
        token : String
    ) {
        _getGroupMessagesState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getGroupMessageUseCase(token, groupId)

            println(result)

            result.onSuccess { response ->
                _getGroupMessagesState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _getGroupMessagesState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun observeGroupMessages(groupId: String) {
        chatSocketManager.connect(
            groupId = groupId,
            onMessageReceived = { liveMessage ->
                appendIncomingMessage(liveMessage)
            },
            onError = { error ->
                println("Socket Error: $error")
            }
        )
    }

    private fun appendIncomingMessage(liveMessage: LiveMessageResponse) {
        println("SOCKET MESSAGE RECEIVED = $liveMessage")

        val currentMessages =
            (_getGroupMessagesState.value as? ResultState.Success)?.data.orEmpty()

        val alreadyExists = currentMessages.any { it.messageId == liveMessage.messageId }
        if (alreadyExists) {
            println("MESSAGE ALREADY EXISTS, SKIPPING")
            return
        }

        val newMessage = Message(
            messageId = liveMessage.messageId,
            content = liveMessage.content,
            createdAt = liveMessage.createdAt ?: "",
            read = liveMessage.isRead,
            userId = liveMessage.userId,
            fullName = liveMessage.fullName
        )

        _getGroupMessagesState.value = ResultState.Success(
            listOf(newMessage) + currentMessages
        )

        println("STATE UPDATED WITH NEW SOCKET MESSAGE")
    }

    fun appendLocalMessage(message: Message) {
        val currentMessages =
            (_getGroupMessagesState.value as? ResultState.Success)?.data.orEmpty()

        val alreadyExists = currentMessages.any { it.messageId == message.messageId }
        if (alreadyExists) return

        _getGroupMessagesState.value = ResultState.Success(
            listOf(message) + currentMessages
        )
    }

    fun stopObservingGroupMessages() {
        chatSocketManager.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        chatSocketManager.disconnect()
    }

    fun resetGroupMessageState() {
        _getGroupMessagesState.value = ResultState.Idle
    }
}