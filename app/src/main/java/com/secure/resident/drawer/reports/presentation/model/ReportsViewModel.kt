package com.secure.resident.drawer.reports.presentation.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.usecase.GetReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getReportsUseCase: GetReportsUseCase,
    @ApplicationContext private val context: Context
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

            val token = AuthPrefs.getToken(context) ?: run {
                _error.value = "Session expired, please log in again"
                _isLoading.value = false
                return@launch
            }

            getReportsUseCase(token).fold(
                onSuccess = {
                    _reports.value = it
                    _isLoading.value = false
                },
                onFailure = {
                    _error.value = it.message ?: "Unexpected error"
                    _isLoading.value = false
                }
            )
        }
    }
}