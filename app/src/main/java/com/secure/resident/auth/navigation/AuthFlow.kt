package com.secure.resident.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.auth.presentation.view.login.LoginView
import com.secure.resident.core.navigation.AppRoute

fun NavGraphBuilder.authFlow(navController: NavController) {


    navigation(
        startDestination = AuthRoute.LOGIN ,
        route = AppRoute.AUTH
    ) {
        composable(
            AuthRoute.LOGIN
        ) {
            LoginView(navController)
        }
    }
}