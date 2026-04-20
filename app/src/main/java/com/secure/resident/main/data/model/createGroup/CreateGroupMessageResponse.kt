package com.secure.resident.main.data.model.createGroup

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupMessageResponse(
    val groupId: String?=null,
    val groupName: String?=null,
    val imageUrl: String?=null,
    val createdAt: String?=null
)