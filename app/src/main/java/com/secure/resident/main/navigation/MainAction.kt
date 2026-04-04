package com.secure.resident.main.navigation

import androidx.navigation.NavController

object MainAction {

    fun navigationToGroupChat(navController: NavController) {
        navController.navigate(MainRoute.GROUP_CHAT)
    }

    fun navigationToNotification(navController: NavController) {
        navController.navigate(MainRoute.NOTIFICATION)
    }

    fun navigationToReserveFacilityGraph(navController: NavController){
        navController.navigate(MainRoute.RESERVE_FACILITY)
    }

    fun navigationToSendReport(navController: NavController) {
        navController.navigate(MainRoute.SEND_REPORT)
    }

}