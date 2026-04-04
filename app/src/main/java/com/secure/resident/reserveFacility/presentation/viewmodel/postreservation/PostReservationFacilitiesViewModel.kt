package com.secure.resident.reserveFacility.presentation.viewmodel.postreservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.data.model.ReserveFacilityResponse
import com.secure.resident.reserveFacility.domain.usecase.ValidateReservationDataUseCase
import com.secure.resident.reserveFacility.domain.usecase.postreservation.PostReservationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostReservationFacilitiesViewModel @Inject constructor(
    private val postReservationUseCase: PostReservationUseCase ,
    private val validateReservationDataUseCase: ValidateReservationDataUseCase
) : ViewModel() {

    private val _reservationState = MutableStateFlow<ResultState<ReserveFacilityResponse>>(ResultState.Idle)
    val reservationState : StateFlow<ResultState<ReserveFacilityResponse>> = _reservationState.asStateFlow()

    fun postReservation(
        token : String ,
        request: ReserveFacilityRequest
    ) {
        viewModelScope.launch {

            _reservationState.value = ResultState.Loading

            println(request)
            println(token)

            val result = postReservationUseCase(token , request)

            println(result)

            result.onSuccess { response ->
                _reservationState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _reservationState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetReservationState() {
        _reservationState.value = ResultState.Idle
    }

    private val _validationMessage = MutableStateFlow<String?>(null)
    val validationMessage = _validationMessage.asStateFlow()

    fun validateReservationForm(
        reservationDate: String,
        startTime: String,
        endTime: String,
        numberOfGuest: String
    ): Boolean {
        val result = validateReservationDataUseCase(
            reservationDate = reservationDate,
            startTime = startTime,
            endTime = endTime,
            numberOfGuest = numberOfGuest
        )

        return if (result.isValid) {
            _validationMessage.value = null
            true
        } else {
            _validationMessage.value = result.errorMessage
            false
        }
    }

    fun clearValidationMessage() {
        _validationMessage.value = null
    }
}