package com.secure.resident.drawer.facilities.presentation.view.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.facilities.presentation.view.FacilitiesStatus
import com.secure.resident.drawer.facilities.presentation.view.component.ReservationItem
import com.secure.resident.drawer.facilities.presentation.viewmodel.GetReservedFacilitiesViewModel

@Composable
fun PendingReservationView(
    getReservedFacilitiesViewModel: GetReservedFacilitiesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }

    LaunchedEffect(Unit) {
        if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
            getReservedFacilitiesViewModel.getUserIncident(token , userId , FacilitiesStatus.PENDING)
        }
    }

    val userReportState by getReservedFacilitiesViewModel.userReservationState.collectAsState()


    when(val state = userReportState) {

        is ResultState.Loading -> {
            CircularIndicator()
        }

        is ResultState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PrimaryText(
                    state.message ,
                    needBold = true
                )
            }
        }

        is ResultState.Success -> {
            if (state.data.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Pending Reservation found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.data) { reservation ->
                        ReservationItem(reservation)
                    }
                }
            }
        }

        else -> null
    }
}