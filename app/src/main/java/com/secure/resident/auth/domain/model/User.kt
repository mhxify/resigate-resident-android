package com.secure.resident.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String? = "",
    val fullname: String? ="",
    val imageUrl: String? = "",
    val role: String? = "",
    val userId: String? = ""
)