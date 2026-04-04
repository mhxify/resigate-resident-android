package com.secure.resident.reserveFacility.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.data.model.ReserveFacilityResponse
import com.secure.resident.reserveFacility.domain.model.Facility
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class FacilityRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getFacilities(
        token : String
    ) : List<Facility> {
        val response = httpClient.get("${baseUrl}facilities") {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
        }

        return response.body()
    }

    suspend fun postReservation(
        token: String ,
        request: ReserveFacilityRequest
    ) : ReserveFacilityResponse {
        val response = httpClient.post("${baseUrl}reservations") {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
            setBody(request)
        }

        return response.body()
    }
}