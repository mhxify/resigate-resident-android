package com.secure.resident.drawer.incidents.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateIncidentRequest(
    val category: String,
    val incidentDescription: String,
    val userId: String
)