package com.secure.resident.notification.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.CircularIndicator
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.notification.presentation.view.component.NotificationItem
import com.secure.resident.notification.presentation.viewmodel.GetUserNotificationViewModel
import com.secure.resident.notification.presentation.viewmodel.MarkNotificationAsReadViewModel

@Composable
fun NotificationScreen(
    navController: NavController ,
    getUserNotificationViewModel: GetUserNotificationViewModel = hiltViewModel() ,
    markNotificationAsReadViewModel: MarkNotificationAsReadViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val userId = remember { AuthPrefs.getUser(context).userId }
    val token = remember { AuthPrefs.getToken(context)}

    val getUserNotificationState by getUserNotificationViewModel.userNotificationState.collectAsState()

    LaunchedEffect(token , userId) {
        if (!token.isNullOrBlank() && !userId.isNullOrBlank()) {
            getUserNotificationViewModel.getUserNotification(token , userId)
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "Notifications"
            )
        }
    ) { innerPadding ->
        when(val state = getUserNotificationState) {

            is ResultState.Loading -> {
                CircularIndicator()
            }

            is ResultState.Success -> {
                if (state.data.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        PrimaryText(
                            "No Notification for the moment" ,
                            needBold = true
                        )
                    }
                }else {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp) ,
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        items(
                            items = state.data,
                            key = { it.notificationId }
                        ) { notification ->

                            if (!notification.read && !token.isNullOrBlank()) {
                                markNotificationAsReadViewModel.markNotificationAsRead(
                                    token = token,
                                    notificationId = notification.notificationId
                                )
                            }

                            NotificationItem(
                                notification = notification
                            )
                        }
                    }
                }
            }

            is ResultState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    PrimaryText(
                        state.message ,
                        needBold = true
                    )
                }
            }

            else -> null
        }
    }
}