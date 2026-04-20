package com.secure.resident.drawer.requestguest.presentation.view.component

import com.secure.resident.drawer.requestguest.domain.model.RequestGuest
import com.secure.resident.drawer.requestguest.presentation.view.RequestGuestStatus
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.secure.resident.core.presentation.helper.formatDate
import com.secure.resident.core.presentation.helper.formatReservationDateTime

@Composable
fun RequestGuestItem(
    request: RequestGuest,
    modifier: Modifier = Modifier,
    onCancelClick: ((String) -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Guest Request",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Created ${formatReservationDateTime(request.createdAt)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                    )
                }

                RequestStatusBadge(status = request.requestStatus)
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
            )

            // Main content
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                InfoBlock(
                    label = "Guest Full Name",
                    value = request.guestFullName
                )

                InfoBlock(
                    label = "Guest Email",
                    value = request.guestEmail
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    InfoSmallCard(
                        title = "Date",
                        value = formatDate(request.guestDate),
                        modifier = Modifier.weight(1f, fill = true)
                    )

                    InfoSmallCard(
                        title = "Start Time",
                        value = formatReservationDateTime(request.startTime),
                        modifier = Modifier.weight(1f, fill = true)
                    )

                    InfoSmallCard(
                        title = "End Time",
                        value = formatReservationDateTime(request.endTime),
                        modifier = Modifier.weight(1f, fill = true)
                    )
                }
            }

            // Action
            if (request.requestStatus.equals(RequestGuestStatus.PENDING, ignoreCase = true) && onCancelClick != null) {
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = { onCancelClick(request.requestId) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Cancel Request",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoBlock(
    label: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun InfoSmallCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.30f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun RequestStatusBadge(status: String) {
    val normalizedStatus = status.uppercase()

    val backgroundColor = when (normalizedStatus) {
        RequestGuestStatus.PENDING -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f)
        RequestGuestStatus.APPROVED -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f)
        RequestGuestStatus.REJECTED -> MaterialTheme.colorScheme.error.copy(alpha = 0.16f)
        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
    }

    val contentColor = when (normalizedStatus) {
        RequestGuestStatus.PENDING -> MaterialTheme.colorScheme.secondary
        RequestGuestStatus.APPROVED -> MaterialTheme.colorScheme.tertiary
        RequestGuestStatus.REJECTED -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = normalizedStatus,
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}