package com.secure.resident.main.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.main.presentation.view.component.AppBottomBar
import com.secure.resident.main.presentation.view.component.AppTopBar

@Composable
fun MainView(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            AppTopBar(navController)
        } ,
        bottomBar = {
            AppBottomBar(
                selectedIndex = selectedTab ,
                onTabSelected = { selectedTab = it } ,
                addClick = {

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when(selectedTab) {
                0 -> {

                }

                1 -> {

                }

                2 -> {

                }

                3 -> {

                }
            }
        }
    }
}