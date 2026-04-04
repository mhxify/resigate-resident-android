package com.secure.resident.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.core.navigation.AppRoute
import com.secure.resident.drawer.navigation.drawerGraph
import com.secure.resident.main.presentation.view.MainView
import com.secure.resident.main.presentation.view.section.chat.GroupMessageScreen
import com.secure.resident.notification.presentation.view.NotificationScreen
import com.secure.resident.reserveFacility.navigation.reserveFacility

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = MainRoute.MAIN ,
        route = AppRoute.MAIN
    ) {
        composable(MainRoute.MAIN) {
            MainView(navController)
        }

        composable(MainRoute.GROUP_CHAT) {
            GroupMessageScreen(navController)
        }

        drawerGraph(navController)

        composable(MainRoute.NOTIFICATION) {
            NotificationScreen(navController)
        }

        reserveFacility(navController)
    }
}