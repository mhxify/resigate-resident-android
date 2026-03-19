package com.secure.resident.auth.presentation.viewmodel.forgot.verifyOtp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.domain.usecase.forgot.verifyOtp.VerifyOtpUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {
    private val _verifyOtpState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val verifyOtpState : StateFlow<ResultState<Unit>> = _verifyOtpState.asStateFlow()

    fun verifyOtp(
        email : String ,
        otp : String
    ) {
        viewModelScope.launch {

            _verifyOtpState.value = ResultState.Loading

            val result = verifyOtpUseCase(email , otp )

            println(result)

            result.onSuccess { response ->
                _verifyOtpState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _verifyOtpState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetVerifyOtpState() {
        _verifyOtpState.value = ResultState.Idle
    }
}