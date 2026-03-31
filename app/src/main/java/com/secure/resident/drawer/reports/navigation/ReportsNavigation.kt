package com.secure.resident.drawer.reports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.core.navigation.AppRoute
import com.secure.resident.drawer.navigation.DrawerRoute
import com.secure.resident.drawer.reports.presentation.view.ReportsScreen

fun NavGraphBuilder.reportsGraph(navController: NavController) {

    navigation(
        startDestination = ReportRoute.REPORTS,
        route = DrawerRoute.REPORTS
    ) {
        composable(ReportRoute.REPORTS) {
            ReportsScreen(navController)
        }
    }

}