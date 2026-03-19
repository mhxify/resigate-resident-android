package com.secure.resident.auth.data.model.login.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginDtoResponse(
    val token: String
)
