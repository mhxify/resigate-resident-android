package com.secure.resident.notification.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.notification.domain.model.Notification
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject

class NotificationRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getUserNotification(
        token : String ,
        userId : String
    ) : List<Notification> {
        val response = httpClient.get("${baseUrl}notifications/$userId") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

    suspend fun markNotificationAsReaded(
        token: String ,
        notificationId : String
    ) {
        val response = httpClient.patch("${baseUrl}notifications/$notificationId/read"){

            bearerAuth(token)
            url {
                parameters.append("isRead", "true")
            }
            contentType(ContentType.Application.Json)
        }

        if (!response.status.isSuccess()) {
            throw Exception("Failed to mark notification as read")
        }
    }
}

