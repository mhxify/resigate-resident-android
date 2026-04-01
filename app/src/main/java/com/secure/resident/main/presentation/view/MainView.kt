package com.secure.resident.main.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.auth.navigation.AuthRoute
import com.secure.resident.core.presentation.component.NavBar
import com.secure.resident.drawer.navigation.DrawerAction
import com.secure.resident.main.presentation.view.component.AddDialogView
import com.secure.resident.main.presentation.view.component.AppDrawerContent
import com.secure.resident.main.presentation.view.component.AppTopBar
import com.secure.resident.main.presentation.view.component.DrawerItemRoutes
import com.secure.resident.main.presentation.view.section.cart.CardScreen
import com.secure.resident.main.presentation.view.section.chat.ChatScreen
import com.secure.resident.main.presentation.view.section.home.HomeScreen
import com.secure.resident.main.presentation.view.section.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun MainView(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedRoute by remember { mutableStateOf("") }

    var isOpen by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                selectedRoute = selectedRoute,
                onClick = { item ->
                    scope.launch {
                        when(item.route) {
                            DrawerItemRoutes.REPORTS -> {
                                DrawerAction.navigateToReports(navController)
                            }

                            DrawerItemRoutes.INCIDENT -> {
                                DrawerAction.navigationToIncident(navController)
                            }

                            DrawerItemRoutes.ABOUT_US -> {
                                DrawerAction.navigationToAboutUs(navController)
                            }

                            DrawerItemRoutes.FACILITIES -> {
                                DrawerAction.navigationToReserveFacilities(navController)
                            }

                            DrawerItemRoutes.REQUEST_GUEST -> {
                                DrawerAction.navigationToRequestGuest(navController)
                            }

                            DrawerItemRoutes.LOGOUT  -> {
                                AuthPrefs.logout(context)
                                drawerState.close()
                                navController.navigate(AuthRoute.LOGIN)
                            }
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    navController ,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            } ,
            bottomBar = {
                NavBar(
                    currentRoute = selectedTab ,
                    onItemClick = { item ->
                        selectedTab = item.route
                    } ,
                    onAddClick = {
                        isOpen = true
                    }
                )
//                AppBottomBar(
//                    selectedIndex = selectedTab ,
//                    onTabSelected = { selectedTab = it } ,
//                    addClick = {
//
//                    }
//                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when(selectedTab) {
                    0 -> {
                        HomeScreen(navController)
                    }

                    1 -> {
                        CardScreen()
                    }

                    2 -> {
                        ChatScreen(navController)
                    }

                    3 -> {
                        ProfileScreen(navController)
                    }
                }
            }
        }
    }

    if (isOpen) {
        AddDialogView(
            onDismiss = {
                isOpen = false
            } ,
            onItemClicked = {
                isOpen = false
            }
        )
    }
}