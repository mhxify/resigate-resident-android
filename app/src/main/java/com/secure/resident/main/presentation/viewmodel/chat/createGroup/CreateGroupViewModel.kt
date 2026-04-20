package com.secure.resident.main.presentation.viewmodel.chat.createGroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageRequest
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageResponse
import com.secure.resident.main.domain.usecase.chat.createGroup.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {
    private val _createGroupState = MutableStateFlow<ResultState<CreateGroupMessageResponse>>(ResultState.Idle)
    val createGroupState : StateFlow<ResultState<CreateGroupMessageResponse>> = _createGroupState.asStateFlow()

    fun createGroup(
        token : String ,
        request : CreateGroupMessageRequest
    ) {
        _createGroupState.value = ResultState.Loading

        viewModelScope.launch {

            println(request)
            val result = createGroupUseCase(token, request)

            println(result)
            result.onSuccess { response ->
                _createGroupState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _createGroupState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun clearCreateGroupState() {
        _createGroupState.value = ResultState.Idle
    }
}