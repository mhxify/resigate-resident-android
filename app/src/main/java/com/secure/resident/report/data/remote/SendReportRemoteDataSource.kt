package com.secure.resident.report.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.data.model.SendReportResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class SendReportRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun sendReport(
        token : String ,
        request : SendReportRequest
    ) : SendReportResponse {
        val response = httpClient.submitFormWithBinaryData(
            url = "${baseUrl}reports" ,
            formData = formData {
                append("userId" , request.userId)
                append("content" , request.content)
                append(
                    key = "image",
                    value = request.image,
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