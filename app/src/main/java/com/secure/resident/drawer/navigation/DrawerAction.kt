package com.secure.resident.drawer.navigation

import androidx.navigation.NavController

object DrawerAction {
    fun navigateToReports(navController: NavController) {
        navController.navigate(DrawerRoute.REPORTS)
    }

}