package com.secure.resident.drawer.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.core.presentation.component.BackTopBar
import com.secure.resident.core.presentation.component.PrimaryText
import kotlinx.coroutines.delay

@Composable
fun AboutUsScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            BackTopBar(
                onBackClick = {
                    navController.popBackStack()
                } ,
                topBarName = "About Us"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.resident_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
            )

            PrimaryText(
                text = "ResiGate" ,
                needBold = true
            )

            TypingText(
                text = "Smart access control for modern communities. Manage residents, guests, and security in one powerful platform."
            )
        }
    }
}

@Composable
fun TypingText(
    text: String,
    typingSpeed: Long = 40L
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        displayedText = ""
        text.forEachIndexed { index, _ ->
            delay(typingSpeed)
            displayedText = text.take(index + 1)
        }
    }

    PrimaryText(
        text = displayedText,
        needBold = true ,
        textAlign = TextAlign.Center
    )
}