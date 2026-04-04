package com.secure.resident.report.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.data.model.SendReportResponse
import com.secure.resident.report.domain.usecase.SendReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendReportViewModel @Inject constructor(
    private val sendReportUseCase: SendReportUseCase
) : ViewModel() {

    private val _sendReportState = MutableStateFlow<ResultState<SendReportResponse>>(ResultState.Idle)
    val sendReportState : StateFlow<ResultState<SendReportResponse>> = _sendReportState.asStateFlow()

    fun sendReport(
        token : String ,
        request: SendReportRequest
    ) {
        _sendReportState.value = ResultState.Loading

        viewModelScope.launch {

            val result = sendReportUseCase(token, request)

            println(result)
            result.onSuccess { response ->
                _sendReportState.value = ResultState.Success(response)
            }.onFailure { exception ->
                _sendReportState.value = ResultState.Error(
                    exception.message ?: "Something went wrong"
                )
            }
        }
    }
}