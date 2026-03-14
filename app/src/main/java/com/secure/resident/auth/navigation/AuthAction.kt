package com.secure.resident.auth.navigation

import androidx.navigation.NavController
import com.secure.resident.core.navigation.AppRoute

object AuthAction {

    fun navigationToForgotPassFlow(navController: NavController) {
        navController.navigate(AuthRoute.FORGOT)
    }

    fun navigationToMainFlow(navController: NavController) {
        navController.navigate(AppRoute.MAIN)
    }
}