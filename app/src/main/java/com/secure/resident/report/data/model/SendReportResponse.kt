package com.secure.resident.report.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendReportResponse(
    val content: String,
    val imageUrl: String,
    val reportId: String,
    val sentAt: String
)