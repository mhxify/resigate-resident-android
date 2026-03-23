package com.secure.resident.drawer.reports.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.secure.resident.drawer.reports.presentation.ReportsScreen

const val REPORTS_ROUTE = "reports"

fun NavGraphBuilder.reportsScreen() {
    composable(route = REPORTS_ROUTE) {
        ReportsScreen()
    }
}