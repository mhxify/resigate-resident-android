package com.secure.resident.drawer.requestguest.data.repository

import com.secure.resident.drawer.requestguest.data.remote.RequestGuestRemoteDataSource
import com.secure.resident.drawer.requestguest.domain.model.RequestGuest
import com.secure.resident.drawer.requestguest.domain.repository.RequestGuestRepository
import javax.inject.Inject

class RequestGuestRepositoryImpl @Inject constructor(
    private val requestGuestRemoteDataSource: RequestGuestRemoteDataSource
) : RequestGuestRepository {

    override suspend fun getUserRequestGuest(
        token: String,
        userId: String
    ): Result<List<RequestGuest>> {
        return try {
            val response = requestGuestRemoteDataSource.getUserRequestGuest(
                userId = userId ,
                token = token
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}