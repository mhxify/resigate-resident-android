package com.secure.resident.drawer.facilities.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.drawer.facilities.domain.model.Reservation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ReservationRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getUserReservation(
        token : String ,
        userId : String
    ) : List<Reservation> {
        val response = httpClient.get("${baseUrl}reservations/me") {
            url {
                parameters.append("userId", userId)
            }
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }
}