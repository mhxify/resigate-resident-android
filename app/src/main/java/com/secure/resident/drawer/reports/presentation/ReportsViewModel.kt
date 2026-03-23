package com.secure.resident.drawer.reports.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.drawer.reports.domain.GetReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getReportsUseCase: GetReportsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ReportsUiState())
    val state: State<ReportsUiState> = _state

    init {
        loadReports()
    }

    private fun loadReports() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            getReportsUseCase().fold(
                onSuccess = { reports ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        reports = reports
                    )
                },
                onFailure = { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = throwable.message ?: "error"
                    )
                }
            )
        }
    }

    fun retry() {
        loadReports()
    }
}