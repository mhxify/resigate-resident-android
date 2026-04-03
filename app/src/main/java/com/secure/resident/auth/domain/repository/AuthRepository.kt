package com.secure.resident.auth.domain.repository

import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.data.model.updatefcm.UpdateFcm
import com.secure.resident.auth.domain.model.User

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<LoginDtoResponse>

    suspend fun getMe(
        token : String
    ) : Result<User>

    suspend fun sendOtp(
        email : String
    ) : Result<Unit>

    suspend fun verifyOtp(
        email: String ,
        otp : String
    ) : Result<Unit>

    suspend fun resetPassword(
        email: String ,
        password : String
    ) : Result<Unit>

    suspend fun updateFcm(
        request : UpdateFcm ,
        token : String
    ) : Result<Unit>
}