package com.secure.resident.auth.domain.repository

import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.domain.model.User

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<LoginDtoResponse>

    suspend fun getMe(
        token : String
    ) : Result<User>
}