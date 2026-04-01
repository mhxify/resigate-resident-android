package com.secure.resident.drawer.incidents.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    val categoryName: String,
    val incidentDescription: String,
    val incidentId: String,
    val reportedAt: String,
    val status: String
)