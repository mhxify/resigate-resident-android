package com.secure.resident.drawer.incidents.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateIncidentResponse (
    val incidentId : String ?=null
)