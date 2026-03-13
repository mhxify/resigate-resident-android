package com.secure.resident.auth.presentation.view.forgot.navigation

import androidx.navigation.NavController
import com.secure.resident.auth.navigation.AuthRoute

object ForgotAction {

    fun navigationToConfirmOtp(navController: NavController) {
        navController.navigate(ForgotRoute.OTP)
    }

    fun navigationToResetPassword(navController: NavController) {
        navController.navigate(ForgotRoute.RESET_PASSWORD)
    }

    fun popBackToLogin(navController: NavController) {
        navController.navigate(AuthRoute.LOGIN)
    }
}