package com.secure.resident.guestrequest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.data.model.RequestGuestResponse
import com.secure.resident.guestrequest.domain.usecase.RequestGuestUseCase
import com.secure.resident.guestrequest.domain.usecase.ValidationGuestRequestDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestGuestViewModel @Inject constructor(
    private val requestGuestUseCase: RequestGuestUseCase ,
    private val validationGuestRequestDataUseCase: ValidationGuestRequestDataUseCase
) : ViewModel() {

    private val _requestGuestState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val requestGuestState : StateFlow<ResultState<String>> = _requestGuestState.asStateFlow()

    fun requestGuest(
        token : String ,
        request: GuestRequest
    ) {
        _requestGuestState.value = ResultState.Loading

        viewModelScope.launch {

            println(token)
            println(request)
            val result = requestGuestUseCase(token, request)

            println(result)
            result.onSuccess { response ->
                _requestGuestState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _requestGuestState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    private val _validationMessage = MutableStateFlow<String?>(null)
    val validationMessage = _validationMessage.asStateFlow()

    fun validationGuestRequestForm(
        guestEmail : String,
        startTime: String,
        endTime: String ,
        fullName : String ,
        guestDate : String
    ): Boolean {
        val result = validationGuestRequestDataUseCase(
            startTime = startTime,
            endTime = endTime,
            guestEmail = guestEmail ,
            fullName  = fullName ,
            date = guestDate
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