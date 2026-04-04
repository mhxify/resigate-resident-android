package com.secure.resident.reserveFacility.navigation

import androidx.navigation.NavController
import com.secure.resident.core.navigation.AppRoute

object ReserveFacilitiesAction {
    fun navigationToCompleteFacility(navController: NavController) {
        navController.navigate(ReserveFacilitiesRoute.COMPLETE_RESERVE_FACILITY)
    }

    fun popBackToMain(navController: NavController) {
        navController.navigate(AppRoute.MAIN) {
            popUpTo(0 ) {
                inclusive = true
            }
        }
    }
}