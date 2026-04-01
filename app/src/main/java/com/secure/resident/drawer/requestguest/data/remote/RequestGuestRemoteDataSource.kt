package com.secure.resident.drawer.requestguest.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.drawer.requestguest.domain.model.RequestGuest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class RequestGuestRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getUserRequestGuest(
        token : String ,
        userId : String
    ) : List<RequestGuest> {
        val response = httpClient.get("${baseUrl}guest-requests/me") {
            url {
                parameters.append("userId", userId)
            }
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}

