package com.secure.resident.drawer.incidents.presentation.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.secure.resident.core.presentation.helper.formatDate
import com.secure.resident.drawer.incidents.domain.model.Incident
import com.secure.resident.drawer.incidents.presentation.view.IncidentStatus

@Composable
fun IncidentItem(
    incident: Incident,
    modifier: Modifier = Modifier
) {
    val statusColor = when (incident.status.lowercase()) {
        IncidentStatus.PENDING -> MaterialTheme.colorScheme.tertiary
        IncidentStatus.RESOLVED -> MaterialTheme.colorScheme.primary
        IncidentStatus.CANCELED  -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = incident.categoryName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = incident.status,
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor
                )
            }

            Text(
                text = incident.incidentDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = formatDate(incident.reportedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

