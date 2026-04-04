package com.secure.resident.drawer.facilities.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.StatusToggleSwitcher
import com.secure.resident.drawer.facilities.presentation.view.sections.CanceledReservationView
import com.secure.resident.drawer.facilities.presentation.view.sections.CompletedReservationView
import com.secure.resident.drawer.facilities.presentation.view.sections.ConfirmedReservationView
import com.secure.resident.drawer.facilities.presentation.view.sections.PendingReservationView

object FacilitiesStatus {
    const val PENDING = "PENDING"
    const val CONFIRMED = "CONFIRMED"
    const val CANCELED = "CANCELED"
    const val COMPLETED = "COMPLETED"
}

@Composable
fun FacilitiesScreen(
    navController: NavController
) {
    val statusList = listOf(
        FacilitiesStatus.PENDING ,
        FacilitiesStatus.CONFIRMED ,
        FacilitiesStatus.COMPLETED ,
        FacilitiesStatus.CANCELED ,
    )

    var selected by remember { mutableStateOf(FacilitiesStatus.PENDING) }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Reserved Facilities"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp) ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusToggleSwitcher(
                statusList = statusList,
                selectedStatus = selected,
                onStatusSelected = { selected = it }
            )

            when(selected) {
                FacilitiesStatus.PENDING -> {
                    PendingReservationView()
                }

                FacilitiesStatus.CONFIRMED -> {
                    ConfirmedReservationView()
                }

                FacilitiesStatus.COMPLETED -> {
                    CompletedReservationView()
                }

                FacilitiesStatus.CANCELED -> {
                    CanceledReservationView()
                }
            }
        }
    }
}