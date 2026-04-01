package com.secure.resident.drawer.incidents.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.drawer.incidents.presentation.view.IncidentScreen
import com.secure.resident.drawer.navigation.DrawerRoute

fun NavGraphBuilder.incidentGraph(navController: NavController) {
    navigation(
        startDestination = IncidentRoute.INCIDENT,
        route = DrawerRoute.INCIDENCE
    ) {
        composable(IncidentRoute.INCIDENT) {
            IncidentScreen(navController)
        }
    }
}