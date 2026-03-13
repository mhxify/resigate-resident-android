package com.secure.resident.auth.navigation

import androidx.navigation.NavController

object AuthAction {

    fun navigationToForgotPassFlow(navController: NavController) {
        navController.navigate(AuthRoute.FORGOT)
    }
}