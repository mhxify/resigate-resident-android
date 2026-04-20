package com.secure.resident.main.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageRequest
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageResponse
import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.presentation.helper.getMimeType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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

    suspend fun createGroupMessage(
        token : String ,
        request : CreateGroupMessageRequest
    ) : CreateGroupMessageResponse {

        val response = httpClient.submitFormWithBinaryData(
            url = "${baseUrl}message-groups/create" ,
            formData = formData {
                append("userId" , request.userId)
                append("groupName" , request.groupName)
                append(
                    key = "image" ,
                    value = request.image ?: ByteArray(0),
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"report_image.jpg\""
                        )
                    }
                )
                request.memberIds.forEach { memberId ->
                    append("memberIds", memberId)
                }
            }
        ) {
            bearerAuth(token)
        }

        return response.body()
    }
}

