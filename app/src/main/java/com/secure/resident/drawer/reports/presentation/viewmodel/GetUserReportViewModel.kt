package com.secure.resident.drawer.reports.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.usecase.GetUserReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserReportViewModel @Inject constructor(
    private val getReportsUseCase: GetUserReportsUseCase
) : ViewModel() {

    private val _userReportState = MutableStateFlow<ResultState<List<Report>>> (ResultState.Idle)
    val userReportState : StateFlow<ResultState<List<Report>>> = _userReportState.asStateFlow()

    fun getUserReport(
        token : String ,
        userId : String
    ) {
        _userReportState.value = ResultState.Loading

        viewModelScope.launch {
            val result = getReportsUseCase(token , userId)

            println(result)

            println(result)
            result.onSuccess { response ->
                _userReportState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _userReportState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }

    fun resetUserReportState() {
        _userReportState.value = ResultState.Idle
    }
}