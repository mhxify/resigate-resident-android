package com.secure.resident.main.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.secure.resident.ui.theme.Background
import com.secure.resident.ui.theme.MainColor

data class BottomNavItem(
    val icon: ImageVector
)

@Preview(showBackground = true)
@Composable
fun AppBottomBar(
    selectedIndex : Int =0,
    onTabSelected: (Int) -> Unit = {},
    addClick : () -> Unit = {}
) {
    val items = listOf(
        BottomNavItem( Icons.Default.Home),
        BottomNavItem(Icons.Default.Badge),
        BottomNavItem( Icons.Default.ChatBubble),
        BottomNavItem( Icons.Default.Person)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth() ,
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(80.dp)
                .shadow(elevation = 2.dp , shape = RoundedCornerShape(16.dp) )
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.onPrimary)
                //.border(color = MainColor , width = 2.dp , shape = RoundedCornerShape(16.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    item = items[0],
                    selected = selectedIndex == 0,
                    onClick = { onTabSelected(0) }
                )

                BottomBarItem(
                    item = items[1],
                    selected = selectedIndex == 1,
                    onClick = { onTabSelected(1) }
                )

                Spacer(modifier = Modifier.width(72.dp))

                BottomBarItem(
                    item = items[2],
                    selected = selectedIndex == 2,
                    onClick = { onTabSelected(2) }

                )

                BottomBarItem(
                    item = items[3],
                    selected = selectedIndex == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(y = (-50).dp)
                .size(80.dp)
                .shadow(elevation = 2.dp , shape = CircleShape )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    addClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(0.4f)

    IconButton(onClick = onClick) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(30.dp)
        )
    }
}
