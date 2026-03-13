package com.secure.resident.landing.navigation

import androidx.navigation.NavController
import com.secure.resident.core.navigation.AppRoute

object LandingAction {

    fun navigationToOnBoardingView(navController: NavController) {
        navController.navigate(AppRoute.ONBOARDING)
    }

}