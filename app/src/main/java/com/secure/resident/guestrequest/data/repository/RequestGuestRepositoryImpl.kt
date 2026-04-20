package com.secure.resident.guestrequest.data.repository

import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.data.model.RequestGuestResponse
import com.secure.resident.guestrequest.data.remote.RequestGuestApi
import com.secure.resident.guestrequest.domain.repository.RequestGuestRepository
import javax.inject.Inject

class RequestGuestRepositoryImpl @Inject constructor(
    private val requestGuestApi: RequestGuestApi
) : RequestGuestRepository {

    override suspend fun requestGuest(
        token: String,
        request: GuestRequest
    ): Result<String> {
        return try {
            val response = requestGuestApi.requestGuest(token , request)
            println("from repo impl : $response")
            Result.success(response)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}