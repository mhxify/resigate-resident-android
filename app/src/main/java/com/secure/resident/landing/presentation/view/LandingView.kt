package com.secure.resident.landing.presentation.view

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.auth.data.local.AuthPrefs
import com.secure.resident.auth.navigation.AuthAction
import com.secure.resident.landing.navigation.LandingAction
import kotlinx.coroutines.delay

@Composable
fun LandingView(
    navController: NavController
){
    var big by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isLoggedIn by remember { mutableStateOf(AuthPrefs.isLoggedIn(context)) }

    LaunchedEffect(Unit) {
        big = !big
        delay(1000)
        if (isLoggedIn) {
            AuthAction.navigationToMainFlow(navController)
        }else {
            LandingAction.navigationToOnBoardingView(navController)
        }
    }

    val size by animateDpAsState(
        targetValue = if (big) 500.dp else 50.dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.resident_app_logo) ,
            contentDescription = "app logo" ,
            modifier = Modifier
                .size(size)
        )
    }
}