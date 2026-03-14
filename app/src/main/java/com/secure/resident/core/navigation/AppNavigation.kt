package com.secure.resident.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.secure.resident.auth.navigation.authFlow
import com.secure.resident.auth.presentation.view.forgot.navigation.forgotFlow
import com.secure.resident.landing.presentation.view.LandingView
import com.secure.resident.main.navigation.mainGraph
import com.secure.resident.onboarding.presentation.view.OnBoardingView


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController ,
        startDestination = AppRoute.LANDING
    ) {
        composable(
            AppRoute.LANDING
        ) {
            LandingView(navController)
        }

        composable(
            AppRoute.ONBOARDING
        ) {
            OnBoardingView(navController)
        }

        authFlow(navController)

        forgotFlow(navController)

        mainGraph(navController)
    }
}