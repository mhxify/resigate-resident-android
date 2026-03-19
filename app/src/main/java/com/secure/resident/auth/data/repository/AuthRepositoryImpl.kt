package com.secure.resident.auth.data.repository

import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.data.remote.AuthRemoteDataSource
import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<LoginDtoResponse> {
        return try {
            val response = remoteDataSource.login(
                email = email,
                password = password
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}