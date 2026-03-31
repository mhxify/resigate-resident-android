package com.secure.resident.main.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.model.post.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getUserGroups(
        token : String ,
        userId : String
    ) : List<GroupMessage> {
        val response = httpClient.get("${baseUrl}message-groups/me") {
            url {
                parameters.append("userId", userId)
            }
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

    suspend fun getGroupMessage(
        token: String ,
        groupId : String
    ) : List<Message>{
        val response = httpClient.get("${baseUrl}message-groups/$groupId/groupMessages") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

    suspend fun sendMessage(
        request : SendMessageRequest ,
        token: String
    ) : LiveMessageResponse {
        val response = httpClient.post("${baseUrl}messages") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }


    suspend fun getUserPost(
        token: String ,
        userId : String
    ) : List<Post> {
        val response = httpClient.get("${baseUrl}posts/user/$userId") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

    suspend fun getAllPost(
        token: String
    ) : List<Post> {
        val response = httpClient.get("${baseUrl}posts/all") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }
}

