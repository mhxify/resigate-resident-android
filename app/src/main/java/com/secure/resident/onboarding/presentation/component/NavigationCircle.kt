package com.secure.resident.onboarding.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.secure.resident.ui.theme.Background
import com.secure.resident.ui.theme.MainColor

@Composable
fun NavigationCircle(
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .clip(CircleShape)
            .background(MainColor)
            .padding(12.dp) ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            tint = Background,
            contentDescription = null
        )
    }
}