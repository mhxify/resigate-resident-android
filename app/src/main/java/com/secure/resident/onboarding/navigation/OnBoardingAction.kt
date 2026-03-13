package com.secure.resident.onboarding.navigation

import androidx.navigation.NavController
import com.secure.resident.core.navigation.AppRoute

object OnBoardingAction {

    fun navigationToAuthFlow(navController: NavController) {
        navController.navigate(AppRoute.AUTH)
    }
}