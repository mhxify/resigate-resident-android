package com.secure.resident.main.data.model.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiveMessageResponse(
    val messageId: String,
    val groupId: String,
    val content: String,
    val createdAt: String ?=null,

    @SerialName("read")
    val isRead: Boolean,
    val userId: String,
    val fullName: String
)