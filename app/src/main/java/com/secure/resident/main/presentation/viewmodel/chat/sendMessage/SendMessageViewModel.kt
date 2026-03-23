package com.secure.resident.main.presentation.viewmodel.chat.sendMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.usecase.chat.sendMessage.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMessageViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {
    private val _sendMessageState = MutableStateFlow<ResultState<LiveMessageResponse>>(ResultState.Idle)
    val sendMessageState : StateFlow<ResultState<LiveMessageResponse>> = _sendMessageState.asStateFlow()

    fun sendMessage(
        request: SendMessageRequest ,
        token : String
    ) {
        _sendMessageState.value = ResultState.Loading

        viewModelScope.launch {
            val result = sendMessageUseCase(request, token)
            println(result)
            result.onSuccess { response ->
                _sendMessageState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _sendMessageState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetSendMessageState() {
        _sendMessageState.value = ResultState.Idle
    }
}