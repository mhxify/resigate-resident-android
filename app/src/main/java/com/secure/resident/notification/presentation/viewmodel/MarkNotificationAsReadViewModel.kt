package com.secure.resident.notification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.notification.domain.usecase.readnotification.MarkNotificationAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkNotificationAsReadViewModel @Inject constructor(
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {

    private val _markNotificationState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val markNotificationState : StateFlow<ResultState<Unit>> = _markNotificationState.asStateFlow()

    fun markNotificationAsRead(
        token : String ,
        notificationId : String
    ) {
        _markNotificationState.value = ResultState.Loading

        viewModelScope.launch {
            val result = markNotificationAsReadUseCase(token, notificationId)

            println(result)
            result.onSuccess { response ->
                _markNotificationState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _markNotificationState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }
}