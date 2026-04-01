package com.secure.resident.drawer.facilities.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.facilities.domain.model.Reservation
import com.secure.resident.drawer.facilities.domain.usecase.GetUserReservationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GetReservedFacilitiesViewModel @Inject constructor(
    private val getUserReservationUseCase: GetUserReservationUseCase
): ViewModel() {

    private val _userReservationState = MutableStateFlow<ResultState<List<Reservation>>> (ResultState.Idle)
    val userReservationState : StateFlow<ResultState<List<Reservation>>> = _userReservationState.asStateFlow()

    fun getUserIncident(
        token : String ,
        userId : String ,
        status : String
    ) {
        _userReservationState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getUserReservationUseCase(token , userId)

            println(result)

            println(result)
            result.onSuccess { response ->
                _userReservationState.value = ResultState.Success(response.filter { data -> data.status == status })
            }.onFailure { exception ->
                _userReservationState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUserReservationState() {
        _userReservationState.value = ResultState.Idle
    }
}