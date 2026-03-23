package com.secure.resident.drawer.reports.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportResponse(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val status: String
)