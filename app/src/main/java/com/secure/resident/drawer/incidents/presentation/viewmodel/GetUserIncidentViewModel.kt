package com.secure.resident.drawer.incidents.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.incidents.domain.model.Incident
import com.secure.resident.drawer.incidents.domain.usecase.GetUserIncidentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserIncidentViewModel @Inject constructor(
    private val getUserIncidentUseCase: GetUserIncidentUseCase
) : ViewModel() {

    private val _userIncidentState = MutableStateFlow<ResultState<List<Incident>>> (ResultState.Idle)
    val userIncidentState : StateFlow<ResultState<List<Incident>>> = _userIncidentState.asStateFlow()

    fun getUserIncident(
        token : String ,
        userId : String ,
        status : String
    ) {
        _userIncidentState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getUserIncidentUseCase(token , userId)

            println(result)

            println(result)
            result.onSuccess { response ->
                _userIncidentState.value = ResultState.Success(response.filter { data -> data.status == status })
            }.onFailure { exception ->
                _userIncidentState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUserIncidentState() {
        _userIncidentState.value = ResultState.Idle
    }
}