package com.secure.resident.drawer.reports.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.helper.formatReservationDateTime
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.drawer.reports.presentation.viewmodel.GetUserReportViewModel

@Composable
fun ReportsScreen(
    navController: NavController ,
    getUserReportViewModel: GetUserReportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context) }


    LaunchedEffect(Unit) {
        if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
            getUserReportViewModel.getUserReport(token , userId)
        }
    }

    val userReportState by getUserReportViewModel.userReportState.collectAsState()

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Reports"
            )
        }
    ) { innerPadding ->
        when(val state = userReportState) {

            is ResultState.Loading -> {
                CircularIndicator()
            }

            is ResultState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) ,
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
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No reports found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { report ->
                            ReportItem(
                                content = report.content ,
                                sendAt = report.sentAt ,
                                repliedAt = report.repliedAt ,
                                reply = report.adminReply
                            )
                        }
                    }
                }
            }

            else -> null
        }
    }
}

@Preview
@Composable
private fun ReportItem(
    content: String= "Test",
    sendAt: String = "2025-10-20",
    repliedAt: String? = null,
    reply: String? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Sent at: ${formatReservationDateTime(sendAt)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (!reply.isNullOrBlank()) {
                Text(
                    text = "Admin reply: $reply",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            if (!repliedAt.isNullOrBlank()) {
                Text(
                    text = "Admin reply At : ${formatReservationDateTime(repliedAt)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}