package com.secure.resident.main.navigation

import androidx.navigation.NavController

object MainAction {

    fun navigationToGroupChat(navController: NavController) {
        navController.navigate(MainRoute.GROUP_CHAT)
    }
}