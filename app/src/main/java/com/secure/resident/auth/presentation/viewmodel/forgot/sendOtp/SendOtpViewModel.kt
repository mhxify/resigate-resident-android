package com.secure.resident.auth.presentation.viewmodel.forgot.sendOtp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.domain.usecase.forgot.sendOtp.SendOtpUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendOtpViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase
) : ViewModel() {
    private val _sendOtpState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val sendOtpState : StateFlow<ResultState<Unit>> = _sendOtpState.asStateFlow()

    fun sendOtp(
        email : String
    ) {
        viewModelScope.launch {

            _sendOtpState.value = ResultState.Loading

            val result = sendOtpUseCase(email)

            println(result)

            result.onSuccess { response ->
                _sendOtpState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _sendOtpState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetSendOtpState() {
        _sendOtpState.value = ResultState.Idle
    }
}