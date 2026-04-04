package com.secure.resident.drawer.facilities.presentation.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.secure.resident.core.presentation.helper.formatDate
import com.secure.resident.core.presentation.helper.formatReservationDateTime
import com.secure.resident.drawer.facilities.domain.model.Reservation
import com.secure.resident.drawer.facilities.presentation.view.FacilitiesStatus

@Composable
fun ReservationItem(
    reservation: Reservation,
    modifier: Modifier = Modifier
) {
    val statusUi = reservationStatusUi(status = reservation.status)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusBadge(
                    text = statusUi.label,
                    containerColor = statusUi.containerColor,
                    contentColor = statusUi.contentColor
                )

                Text(
                    text = formatDate(reservation.reservationDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Reservation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            InfoLine(
                label = "Time",
                value = "${reservation.startTime} - ${reservation.endTime}"
            )

            InfoLine(
                label = "Guests",
                value = reservation.numberOfGuests.toString()
            )

            InfoLine(
                label = "Created At",
                value = formatReservationDateTime(reservation.createdAt)
            )

            Text(
                text = "ID: ${reservation.reservationId.take(8)}...",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun InfoLine(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )
    }
}

@Composable
private fun StatusBadge(
    text: String,
    containerColor: Color,
    contentColor: Color
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun reservationStatusUi(status: String): ReservationStatusUi {
    return when (status.trim().uppercase()) {
        FacilitiesStatus.PENDING -> ReservationStatusUi(
            label = "Pending",
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f),
            contentColor = MaterialTheme.colorScheme.secondary
        )

        FacilitiesStatus.CONFIRMED -> ReservationStatusUi(
            label = "Confirmed",
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f),
            contentColor = MaterialTheme.colorScheme.tertiary
        )

        FacilitiesStatus.CANCELED -> ReservationStatusUi(
            label = "Canceled",
            containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )

        else -> ReservationStatusUi(
            label = status.lowercase().replaceFirstChar { it.uppercase() },
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

private data class ReservationStatusUi(
    val label: String,
    val containerColor: Color,
    val contentColor: Color
)

