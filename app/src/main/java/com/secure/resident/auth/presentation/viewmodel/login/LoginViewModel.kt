package com.secure.resident.auth.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.domain.usecase.login.LoginUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<ResultState<LoginDtoResponse>>(ResultState.Idle)
    val loginState: StateFlow<ResultState<LoginDtoResponse>> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {

            _loginState.value = ResultState.Loading

            val result = loginUseCase(email, password)

            result.onSuccess { response ->
                _loginState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _loginState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = ResultState.Idle
    }
}