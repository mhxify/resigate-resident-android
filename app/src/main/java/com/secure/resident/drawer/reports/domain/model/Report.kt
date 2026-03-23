package com.secure.resident.drawer.reports.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Report(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val status: String
)