package com.secure.resident.drawer.reports.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.drawer.reports.domain.Report
import com.secure.resident.drawer.reports.domain.GetReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getReportsUseCase: GetReportsUseCase
) : ViewModel() {

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadReports()
    }

    fun retry() = loadReports()

    private fun loadReports() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            getReportsUseCase().fold(
                onSuccess = {
                    _reports.value = it
                    _isLoading.value = false
                },
                onFailure = {
                    _error.value = it.message ?: "error"
                    _isLoading.value = false
                }
            )
        }
    }
}