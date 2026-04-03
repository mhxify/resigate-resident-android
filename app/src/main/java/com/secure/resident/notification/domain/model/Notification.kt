package com.secure.resident.notification.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val content: String,
    val createdAt: String,
    val notificationId: String,
    val read: Boolean
)