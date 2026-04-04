package com.secure.resident.reserveFacility.presentation.viewmodel.getfacilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.reserveFacility.domain.model.Facility
import com.secure.resident.reserveFacility.domain.usecase.getFacility.GetFacilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetFacilitiesViewModel @Inject constructor(
    private val getFacilityUseCase: GetFacilityUseCase
) : ViewModel() {

    private val _facilityState = MutableStateFlow<ResultState<List<Facility>>>(ResultState.Idle)
    val facilityState : StateFlow<ResultState<List<Facility>>> = _facilityState.asStateFlow()

    fun getFacility(
        token : String
    ) {
        viewModelScope.launch {

            _facilityState.value = ResultState.Loading

            val result = getFacilityUseCase(token)

            println(result)

            result.onSuccess { response ->
                _facilityState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _facilityState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }


}