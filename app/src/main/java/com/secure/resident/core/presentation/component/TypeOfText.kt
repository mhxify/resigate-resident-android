package com.secure.resident.core.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.secure.resident.ui.theme.MainColor

@Composable
fun PrimaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MainColor,
    needBold : Boolean = false ,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 16.sp,
        fontWeight = if (needBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = textAlign
    )
}

@Composable
fun SecondaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MainColor,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign,
        lineHeight = 20.sp
    )
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        lineHeight = lineHeight,
        maxLines = maxLines,
        overflow = overflow
    )
}