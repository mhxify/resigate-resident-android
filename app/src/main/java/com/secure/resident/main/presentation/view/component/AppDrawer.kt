package com.secure.resident.main.presentation.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.secure.resident.R
import com.secure.resident.core.presentation.component.PrimaryText

data class DrawerMenuItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val drawerItems = listOf(
    DrawerMenuItem("Reports", "reports", Icons.Default.Description),
    DrawerMenuItem("Incident", "incident", Icons.Default.Warning),
    DrawerMenuItem("Reserve Facilities", "reserve_facilities", Icons.Default.Event),
    DrawerMenuItem("Request Guest", "request_guest", Icons.Default.PersonAdd),
    DrawerMenuItem("About Us", "about_us", Icons.Default.Info),
    DrawerMenuItem("Logout", "logout", Icons.Default.ExitToApp)
)

@Preview
@Composable
fun AppDrawerContent(
    items: List<DrawerMenuItem> = drawerItems,
    selectedRoute: String ="",
    onClick : (DrawerMenuItem) -> Unit = {}
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp) ,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.resident_app_logo) ,
                contentDescription = null ,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
                    .align(
                        Alignment.CenterHorizontally
                    )
            )

            PrimaryText(
                text = "ResiGate Menu",
                needBold = true
            )

            items.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        PrimaryText(
                            text = item.title ,
                            needBold = true
                        )
                    },
                    selected = selectedRoute == item.route,
                    onClick = {
                        onClick(item)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title ,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                )
            }
        }
    }
}