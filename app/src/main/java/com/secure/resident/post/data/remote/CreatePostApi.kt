package com.secure.resident.post.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.data.model.CreatePostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class CreatePostApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun createPost(
        token : String ,
        request: CreatePostRequest
    ) : CreatePostResponse {
        val response = httpClient.submitFormWithBinaryData(
            url = "${baseUrl}posts/create" ,
            formData = formData {
                append("userId" , request.userId)
                append("content" , request.content)
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
            }
        ) {
            bearerAuth(token)
        }

        return response.body()
    }
}