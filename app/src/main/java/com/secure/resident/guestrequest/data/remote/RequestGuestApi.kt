package com.secure.resident.guestrequest.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.data.model.RequestGuestResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class RequestGuestApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun requestGuest(token : String , request: GuestRequest) : String {
        return httpClient.post("${baseUrl}guest-requests/create") {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
            setBody(request)
        }.body()
    }
}