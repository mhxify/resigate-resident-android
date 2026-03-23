package com.secure.resident.main.data.model.message

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val content: String,
    val groupId: String,
    val userId: String
)