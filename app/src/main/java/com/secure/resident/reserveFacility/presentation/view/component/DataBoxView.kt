package com.secure.resident.reserveFacility.presentation.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.secure.resident.core.presentation.component.PrimaryText

@Preview
@Composable
fun DataBoxView(
    values : String ="",
    placeHolder : String = "Please pick date",
    onClick : () -> Unit = {}
) {
    val borderColor = if (values.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val textValue = values.ifBlank { placeHolder }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(14.dp) ,
                color = borderColor ,
                width = 1.dp
            )
            .padding(16.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.CenterStart
    ){
        PrimaryText(
            textValue , 
            color = borderColor
        )
    }

}