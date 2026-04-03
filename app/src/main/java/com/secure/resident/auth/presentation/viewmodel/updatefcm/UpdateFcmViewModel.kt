package com.secure.resident.auth.presentation.viewmodel.updatefcm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.data.model.updatefcm.UpdateFcm
import com.secure.resident.auth.domain.usecase.fcm.UpdateFcmUseCase
import com.secure.resident.core.presentation.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateFcmViewModel @Inject constructor(
    private val updateFcmUseCase: UpdateFcmUseCase
) : ViewModel() {

    private val _updateState = MutableStateFlow<ResultState<Unit>>(ResultState.Idle)
    val updateState : StateFlow<ResultState<Unit>> = _updateState.asStateFlow()

    fun updateFcm(
        token : String ,
        fcmToken : String
    ) {
        viewModelScope.launch {

            _updateState.value = ResultState.Loading

            println("fcm token : $fcmToken")

            val request = UpdateFcm(fcmToken = fcmToken)

            val result = updateFcmUseCase(request , token)

            println(result)

            result.onSuccess { response ->
                _updateState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _updateState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUpdateState () {
        _updateState.value = ResultState.Idle
    }
}