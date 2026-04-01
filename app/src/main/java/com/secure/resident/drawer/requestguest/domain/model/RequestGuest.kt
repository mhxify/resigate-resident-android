package com.secure.resident.drawer.requestguest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestGuest(
    val createdAt: String,
    val endTime: String,
    val guestEmail: String,
    val requestId: String,
    val requestStatus: String,
    val startTime: String,
    val userId: String
)