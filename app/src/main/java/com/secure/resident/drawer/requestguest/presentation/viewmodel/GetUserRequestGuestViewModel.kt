package com.secure.resident.drawer.requestguest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.requestguest.domain.model.RequestGuest
import com.secure.resident.drawer.requestguest.domain.usecase.GetUserRequestGuestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserRequestGuestViewModel @Inject constructor(
    private val getUserRequestGuestUseCase: GetUserRequestGuestUseCase
) : ViewModel() {
    private val _userRequestState = MutableStateFlow<ResultState<List<RequestGuest>>> (ResultState.Idle)
    val userRequestState : StateFlow<ResultState<List<RequestGuest>>> = _userRequestState.asStateFlow()

    fun getUserRequest(
        token : String ,
        userId : String ,
        status : String
    ) {
        _userRequestState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getUserRequestGuestUseCase(token , userId)

            println(result)

            println(result)
            result.onSuccess { response ->
                _userRequestState.value = ResultState.Success(response.filter { data -> data.requestStatus == status })
            }.onFailure { exception ->
                _userRequestState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUserRequestState() {
        _userRequestState.value = ResultState.Idle
    }
}