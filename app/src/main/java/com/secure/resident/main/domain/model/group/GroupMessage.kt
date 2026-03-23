package com.secure.resident.main.domain.model.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupMessage(
    val createdAt: String?,
    val groupId: String?,
    val groupName: String?,
    val imageUrl: String?
)