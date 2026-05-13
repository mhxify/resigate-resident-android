package com.secure.resident.drawer.incidents.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateIncidentRequest(
    val incidentDescription: String,
    val categoryName: String,
    val userId: String
)