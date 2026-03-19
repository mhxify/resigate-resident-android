package com.secure.resident.auth.presentation.viewmodel.forgot.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.domain.usecase.forgot.resetPassword.ResetPasswordUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    private val _resetPasswordState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val resetPasswordState : StateFlow<ResultState<Unit>> = _resetPasswordState.asStateFlow()

    fun resetPassword(
        email : String ,
        password : String
    ) {
        viewModelScope.launch {

            _resetPasswordState.value = ResultState.Loading

            val result = resetPasswordUseCase(email , password)

            println(result)

            result.onSuccess { response ->
                _resetPasswordState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _resetPasswordState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetPasswordState() {
        _resetPasswordState.value = ResultState.Idle
    }
}