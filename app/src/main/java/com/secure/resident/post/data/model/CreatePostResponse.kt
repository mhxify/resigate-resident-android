package com.secure.resident.post.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostResponse(
    val content: String,
    val createdAt: String,
    val imageUrl: String,
    val postId: String
)