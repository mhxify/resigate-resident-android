package com.secure.resident.drawer.incidents.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.data.model.CreateIncidentResponse
import com.secure.resident.drawer.incidents.domain.model.Incident
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class IncidentRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getUserIncident(
        token : String ,
        userId : String
    ) : List<Incident> {
        val response = httpClient.get("${baseUrl}incidents/user/$userId") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    suspend fun reportIncident(
        token: String ,
        request: CreateIncidentRequest
    ) : CreateIncidentResponse {
        val response = httpClient.post("${baseUrl}incidents/create") {
            contentType(ContentType.Application.Json)
            setBody(request)
            bearerAuth(token)
        }

        return response.body()
    }
}