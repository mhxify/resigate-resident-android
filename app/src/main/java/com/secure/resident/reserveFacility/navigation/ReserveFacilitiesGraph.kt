package com.secure.resident.reserveFacility.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.main.navigation.MainRoute
import com.secure.resident.reserveFacility.presentation.view.CompleteReserveFacilitiesScreen
import com.secure.resident.reserveFacility.presentation.view.SelectFacilityScreen

fun NavGraphBuilder.reserveFacility(navController: NavController) {
    navigation(
        startDestination = ReserveFacilitiesRoute.SELECT_FACILITY,
        route = MainRoute.RESERVE_FACILITY
    ) {
        composable(
            ReserveFacilitiesRoute.SELECT_FACILITY
        ) {
            SelectFacilityScreen(navController)
        }

        composable(
            ReserveFacilitiesRoute.COMPLETE_RESERVE_FACILITY
        ) {
            CompleteReserveFacilitiesScreen(navController)
        }
    }
}