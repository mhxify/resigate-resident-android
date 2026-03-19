package com.secure.resident.auth.data.model.login.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginDtoRequest(
    val email : String ,
    val password : String
)