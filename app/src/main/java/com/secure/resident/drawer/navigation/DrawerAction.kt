package com.secure.resident.drawer.navigation

import androidx.navigation.NavController

object DrawerAction {
    fun navigateToReports(navController: NavController) {
        navController.navigate(DrawerRoute.REPORTS)
    }

    fun navigationToIncident(navController: NavController) {
        navController.navigate(DrawerRoute.INCIDENCE)
    }

    fun navigationToAboutUs(navController: NavController) {
        navController.navigate(DrawerRoute.ABOUT_US)
    }
}