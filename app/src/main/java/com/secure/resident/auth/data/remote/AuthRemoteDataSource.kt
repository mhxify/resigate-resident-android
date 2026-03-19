package com.secure.resident.auth.data.remote

import android.app.Application
import com.secure.resident.auth.data.model.login.request.LoginDtoRequest
import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.core.data.remote.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun login(
        email: String,
        password: String
    ): LoginDtoResponse {

        val response = httpClient.post("${baseUrl}auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginDtoRequest(
                    email = email,
                    password = password
                )
            )
        }

        println("STATUS = ${response.status}")
        println("RAW BODY = ${response.bodyAsText()}")

        return response.body()
    }



}