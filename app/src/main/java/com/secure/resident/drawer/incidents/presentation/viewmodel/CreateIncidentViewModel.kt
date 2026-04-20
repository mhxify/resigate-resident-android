package com.secure.resident.drawer.incidents.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.data.model.CreateIncidentResponse
import com.secure.resident.drawer.incidents.domain.usecase.CreateIncidentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateIncidentViewModel @Inject constructor(
    private val createIncidentUseCase: CreateIncidentUseCase
) : ViewModel() {

    private val _createIncidentState = MutableStateFlow<ResultState<CreateIncidentResponse>>(ResultState.Idle)
    val createIncidentState : StateFlow<ResultState<CreateIncidentResponse>> = _createIncidentState.asStateFlow()

    fun createIncident(
        token : String ,
        request: CreateIncidentRequest
    ) {
        _createIncidentState.value = ResultState.Loading

        viewModelScope.launch {

            println(request)
            println(token)
            val result = createIncidentUseCase(token , request)

            println(result)

            result.onSuccess { response ->
                _createIncidentState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _createIncidentState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun clearIncidentState() {
        _createIncidentState.value = ResultState.Idle
    }
}