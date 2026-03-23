package com.secure.resident.main.domain.model.message

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String,
    val createdAt: String ?= null,
    val fullName: String,
    val messageId: String,
    val read: Boolean,
    val userId: String
)