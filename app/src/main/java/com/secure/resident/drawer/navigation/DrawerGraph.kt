package com.secure.resident.drawer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.drawer.about.AboutUsScreen
import com.secure.resident.drawer.facilities.presentation.view.FacilitiesScreen
import com.secure.resident.drawer.incidents.navigation.incidentGraph
import com.secure.resident.drawer.reports.navigation.reportsGraph
import com.secure.resident.drawer.requestguest.presentation.view.RequestGuestScreen
import com.secure.resident.main.navigation.MainRoute

fun NavGraphBuilder.drawerGraph(navController: NavController) {

    navigation(
        startDestination = DrawerRoute.REPORTS ,
        route = MainRoute.DRAWER
    ) {
        reportsGraph(navController)

        incidentGraph(navController)

        composable(DrawerRoute.ABOUT_US) {
            AboutUsScreen(navController)
        }

        composable(DrawerRoute.RESERVE_FACILITIES) {
            FacilitiesScreen(navController)
        }

        composable(DrawerRoute.REQUEST_GUEST) {
            RequestGuestScreen(navController)
        }
    }
}