package com.secure.resident.drawer.incidents.presentation.view

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
import com.secure.resident.drawer.incidents.presentation.view.section.AssignedIncidentView
import com.secure.resident.drawer.incidents.presentation.view.section.CanceledIncidentView
import com.secure.resident.drawer.incidents.presentation.view.section.InProgressIncidentView
import com.secure.resident.drawer.incidents.presentation.view.section.PendingIncidentView
import com.secure.resident.drawer.incidents.presentation.view.section.ResolvedIncidentView

object IncidentStatus {
    const val PENDING = "PENDING"
    const val RESOLVED = "RESOLVED"
    const val ASSIGNED = "ASSIGNED"
    const val CANCELED = "CANCELED"
    const val IN_PROGRESS = "IN_PROGRESS"
}

@Composable
fun IncidentScreen(
    navController : NavController
) {
    val statusList = listOf(
        IncidentStatus.PENDING ,
        IncidentStatus.RESOLVED ,
        IncidentStatus.ASSIGNED ,
        IncidentStatus.CANCELED ,
        IncidentStatus.IN_PROGRESS
    )

    var selected by remember { mutableStateOf(IncidentStatus.PENDING) }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Incident"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            StatusToggleSwitcher(
                statusList = statusList,
                selectedStatus = selected,
                onStatusSelected = { selected = it }
            )

            when(selected) {
                IncidentStatus.PENDING -> {
                    PendingIncidentView()
                }

                IncidentStatus.RESOLVED -> {
                    ResolvedIncidentView()
                }

                IncidentStatus.ASSIGNED -> {
                    AssignedIncidentView()
                }

                IncidentStatus.CANCELED -> {
                    CanceledIncidentView()
                }

                IncidentStatus.IN_PROGRESS -> {
                    InProgressIncidentView()
                }
            }
        }
    }
}