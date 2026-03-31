package com.secure.resident.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BackTopBar(
    onBackClick : () -> Unit = {},
    topBarName : String = "Report"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.spacedBy(16.dp) ,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back" ,
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
        )

        PrimaryText(
            text = topBarName ,
            needBold = true
        )
    }
}