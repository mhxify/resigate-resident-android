package com.secure.resident.main.domain.model.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val commentNumber: Int,
    val content: String,
    val createdAt: String,
    val imageUrl: String?=null,
    val postId: String,
    val postOwnerId: String,
    val postOwnerName: String,
    val postOwnerPicture: String
)