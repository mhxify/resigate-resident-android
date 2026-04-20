package com.secure.resident.auth.data.repository

import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.data.model.updatefcm.UpdateFcm
import com.secure.resident.auth.data.remote.AuthRemoteDataSource
import com.secure.resident.auth.domain.model.User
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

    override suspend fun getMe(token: String): Result<User> {
        return try {
            val response = remoteDataSource.getMe(token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(email: String): Result<Unit> {
        return try {
            val response = remoteDataSource.sendOtp(email)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Result<Unit> {
        return try {
            val response = remoteDataSource.verifyOtp(email , otp)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String, password: String): Result<Unit> {
        return try {
            val response = remoteDataSource.resetPassword(email , password)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateFcm(request: UpdateFcm , token: String): Result<Unit> {
        return try {
            val response = remoteDataSource.updateFcm(request , token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSystemUsers(token: String): Result<List<User>> {
        return try {
            val response = remoteDataSource.getSystemUsers( token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}