package com.secure.resident.drawer.reports.navigation

import androidx.navigation.NavController

object ReportAction {

    fun navigateToReports(navController: NavController) {
        navController.navigate(ReportRoute.REPORTS)
    }
}