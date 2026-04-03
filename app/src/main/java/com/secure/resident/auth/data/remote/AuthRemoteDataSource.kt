package com.secure.resident.auth.data.remote

import com.secure.resident.auth.data.model.login.request.LoginDtoRequest
import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.data.model.updatefcm.UpdateFcm
import com.secure.resident.auth.domain.model.User
import com.secure.resident.core.data.remote.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
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

    suspend fun getMe(
        token : String
    ) : User {
        val response = httpClient.get("${baseUrl}auth/me") {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
        }

        println("STATUS = ${response.status}")
        println("RAW BODY = ${response.bodyAsText()}")

        return response.body()
    }

    suspend fun sendOtp(email: String) {
        val response = httpClient.post("${baseUrl}auth/send") {
            url {
                parameters.append("email", email)
            }
            contentType(ContentType.Application.Json)
        }

        val rawBody = response.bodyAsText()

        println("REMOTE STATUS = ${response.status}")
        println("REMOTE RAW BODY = $rawBody")

        if (!response.status.isSuccess()) {
            println("REMOTE THROWING EXCEPTION NOW")
            throw Exception("HTTP ${response.status.value}: $rawBody")
        }

        println("REMOTE SUCCESS")
    }

    suspend fun verifyOtp(
        email: String ,
        otp : String
    ) {
        val response = httpClient.post("${baseUrl}auth/verify") {
            url {
                parameters.append("email", email)
                parameters.append("code", otp)
            }
            contentType(ContentType.Application.Json)
        }

        val rawBody = response.bodyAsText()

        println("REMOTE STATUS = ${response.status}")
        println("REMOTE RAW BODY = $rawBody")

        if (!response.status.isSuccess()) {
            println("REMOTE THROWING EXCEPTION NOW")
            throw Exception("HTTP ${response.status.value}: $rawBody")
        }

        println("REMOTE SUCCESS")
    }


    suspend fun resetPassword(
        email: String ,
        password : String
    ) {
        val response = httpClient.post("${baseUrl}users/forgot-password") {
            url {
                parameters.append("email", email)
                parameters.append("password", password)
            }
            contentType(ContentType.Application.Json)
        }

        val rawBody = response.bodyAsText()

        println("REMOTE STATUS = ${response.status}")
        println("REMOTE RAW BODY = $rawBody")

        if (!response.status.isSuccess()) {
            println("REMOTE THROWING EXCEPTION NOW")
            throw Exception("HTTP ${response.status.value}: $rawBody")
        }

        println("REMOTE SUCCESS")
    }

    suspend fun updateFcm(
        request : UpdateFcm ,
        token : String
    ) : Unit{
        val response = httpClient.patch("${baseUrl}users") {
            setBody(request)
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()

    }

}