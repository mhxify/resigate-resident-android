package com.secure.resident.drawer.reports.presentation

import com.secure.resident.drawer.reports.domain.Report

data class ReportsUiState(
    val isLoading: Boolean = false,
    val reports: List<Report> = emptyList(),
    val error: String? = null
)