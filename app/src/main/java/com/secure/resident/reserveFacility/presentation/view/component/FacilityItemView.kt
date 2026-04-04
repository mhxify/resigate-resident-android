package com.secure.resident.reserveFacility.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.core.presentation.component.PrimaryText
import com.secure.resident.reserveFacility.domain.enums.FacilitiesStatus
import com.secure.resident.reserveFacility.domain.model.Facility

fun FacilitiesStatus.getUiColor(): Color {
    return when (this) {
        FacilitiesStatus.AVAILABLE -> Color(0xFF4CAF50)
        FacilitiesStatus.RESERVED -> Color(0xFFFF9800)
        FacilitiesStatus.OUT_OF_SERVICE -> Color(0xFFF44336)
    }
}

fun FacilitiesStatus.getLabel(): String {
    return when (this) {
        FacilitiesStatus.AVAILABLE -> "Available"
        FacilitiesStatus.RESERVED -> "Reserved"
        FacilitiesStatus.OUT_OF_SERVICE -> "Out of Service"
    }
}

@Composable
fun FacilityItemView(
    facility: Facility,
    onClick: () -> Unit
) {
    val fullBaseUrl = "${baseUrl.removeSuffix("/")}${facility.imageUrl}"

    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = {
            if (facility.facilityStatus == FacilitiesStatus.AVAILABLE) {
                onClick()
            }
        },
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp) ,
            modifier = Modifier
                .padding(8.dp)
        ) {

            Box {
                AsyncImage(
                    model = fullBaseUrl,
                    contentDescription = facility.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .background(
                            color = facility.facilityStatus.getUiColor(),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = facility.facilityStatus.getLabel(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            PrimaryText(
                text = facility.name,
                needBold = true
            )

            PrimaryText(
                text = "Capacity: ${facility.capacity}",
                needBold = false
            )
        }
    }
}