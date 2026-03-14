package com.secure.resident.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.core.navigation.AppRoute
import com.secure.resident.main.presentation.view.MainView

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = MainRoute.MAIN ,
        route = AppRoute.MAIN
    ) {
        composable(MainRoute.MAIN) {
            MainView(navController)
        }
    }
}