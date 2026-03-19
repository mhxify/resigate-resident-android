package com.secure.resident.auth.presentation.viewmodel.getMe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.domain.model.User
import com.secure.resident.auth.domain.usecase.me.GetMeUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetMeViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase
) : ViewModel() {

    private val _getMeState = MutableStateFlow<ResultState<User>>(ResultState.Idle)
    val getMeState : StateFlow<ResultState<User>> = _getMeState.asStateFlow()

    fun getMe(token : String) {
        viewModelScope.launch {

            _getMeState.value = ResultState.Loading

            val result = getMeUseCase(token)

            result.onSuccess { response ->
                _getMeState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _getMeState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetGetMeState() {
        _getMeState.value = ResultState.Idle
    }
}