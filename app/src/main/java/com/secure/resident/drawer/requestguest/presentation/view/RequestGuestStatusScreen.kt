package com.secure.resident.drawer.requestguest.presentation.view

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
import com.secure.resident.drawer.requestguest.presentation.view.sections.ApprovedRequestView
import com.secure.resident.drawer.requestguest.presentation.view.sections.CanceledRequestView
import com.secure.resident.drawer.requestguest.presentation.view.sections.ExpiredRequestView
import com.secure.resident.drawer.requestguest.presentation.view.sections.PendingRequestView
import com.secure.resident.drawer.requestguest.presentation.view.sections.RejectedRequestView

object RequestGuestStatus {
    const val PENDING = "PENDING"
    const val APPROVED = "APPROVED"
    const val REJECTED = "REJECTED"
    const val CANCELED = "CANCELED"
    const val EXPIRED = "EXPIRED"
}

@Composable
fun RequestGuestStatusScreen(
    navController: NavController
) {
    val statusList = listOf(
        RequestGuestStatus.PENDING ,
        RequestGuestStatus.APPROVED ,
        RequestGuestStatus.REJECTED ,
        RequestGuestStatus.CANCELED ,
        RequestGuestStatus.EXPIRED
    )

    var selected by remember { mutableStateOf(RequestGuestStatus.PENDING) }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Request Guest"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            StatusToggleSwitcher(
                statusList = statusList,
                selectedStatus = selected,
                onStatusSelected = { selected = it }
            )

            when(selected) {
                RequestGuestStatus.PENDING -> {
                    PendingRequestView()
                }

                RequestGuestStatus.APPROVED -> {
                    ApprovedRequestView()
                }

                RequestGuestStatus.REJECTED -> {
                    RejectedRequestView()
                }

                RequestGuestStatus.CANCELED -> {
                    CanceledRequestView()
                }

                RequestGuestStatus.EXPIRED -> {
                    ExpiredRequestView()
                }
            }
        }
    }
}