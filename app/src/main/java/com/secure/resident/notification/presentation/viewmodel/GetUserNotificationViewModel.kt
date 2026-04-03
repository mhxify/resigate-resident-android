package com.secure.resident.notification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.notification.domain.model.Notification
import com.secure.resident.notification.domain.usecase.getusernotification.GetUserNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserNotificationViewModel @Inject constructor(
    private val getUserNotificationUseCase : GetUserNotificationUseCase
) : ViewModel() {

    private val _userNotificationState = MutableStateFlow<ResultState<List<Notification>>>(ResultState.Idle)
    val userNotificationState : StateFlow<ResultState<List<Notification>>> = _userNotificationState.asStateFlow()

    fun getUserNotification(
        token : String ,
        userId : String
    ) {
        _userNotificationState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getUserNotificationUseCase(token, userId)

            println(result)
            result.onSuccess { response ->
                _userNotificationState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _userNotificationState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }
}