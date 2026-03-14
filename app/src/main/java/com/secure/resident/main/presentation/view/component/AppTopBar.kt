package com.secure.resident.main.presentation.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secure.resident.R
import com.secure.resident.ui.theme.MainColor

@Composable
fun AppTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Filled.Menu ,
            contentDescription = null ,
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier
                .clickable {

                }
        )

        Image(
            painter = painterResource(R.drawable.resident_app_logo) ,
            contentDescription = null ,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Icon(
            imageVector = Icons.Filled.Notifications ,
            contentDescription = null ,
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier
                .clickable {

                }
        )
    }
}