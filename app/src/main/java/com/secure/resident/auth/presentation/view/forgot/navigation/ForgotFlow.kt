package com.secure.resident.auth.presentation.view.forgot.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.secure.resident.auth.navigation.AuthRoute
import com.secure.resident.auth.presentation.view.forgot.ConfirmOtpView
import com.secure.resident.auth.presentation.view.forgot.EnterEmailView
import com.secure.resident.auth.presentation.view.forgot.ResetPasswordView

fun NavGraphBuilder.forgotFlow(navController: NavController) {
    navigation(
        startDestination = ForgotRoute.ENTER_EMAIL ,
        route = AuthRoute.FORGOT
    ) {
        composable(
            ForgotRoute.ENTER_EMAIL
        ) {
            EnterEmailView(navController)
        }

        composable(
            ForgotRoute.OTP
        ) {
            ConfirmOtpView(navController)
        }

        composable(
            ForgotRoute.RESET_PASSWORD
        ) {
            ResetPasswordView(navController)
        }
    }
}