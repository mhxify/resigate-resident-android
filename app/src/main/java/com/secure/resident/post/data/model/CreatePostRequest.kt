package com.secure.resident.post.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val userId : String ,
    val content : String ,
    val image : ByteArray? = null
)
