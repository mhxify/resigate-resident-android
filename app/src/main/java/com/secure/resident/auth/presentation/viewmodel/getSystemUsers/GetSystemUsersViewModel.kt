package com.secure.resident.auth.presentation.viewmodel.getSystemUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.domain.model.User
import com.secure.resident.auth.domain.usecase.getSystemUsers.GetSystemUsers
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetSystemUsersViewModel @Inject constructor(
    private val getSystemUsersUseCase: GetSystemUsers
) : ViewModel() {
    private val _systemUsers = MutableStateFlow<ResultState<List<User>>>(ResultState.Idle)
    val systemUser : StateFlow<ResultState<List<User>>> = _systemUsers.asStateFlow()

    fun getSystemUsers(
        token : String
    ) {
        viewModelScope.launch {

            println(token)
            _systemUsers.value = ResultState.Loading

            val result = getSystemUsersUseCase(token)

            println(result)

            result.onSuccess { response ->
                _systemUsers.value = ResultState.Success(response)
            }.onFailure { exception ->
                _systemUsers.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }
}