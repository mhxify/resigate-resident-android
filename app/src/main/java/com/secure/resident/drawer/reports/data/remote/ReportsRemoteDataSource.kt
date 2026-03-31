package com.secure.resident.drawer.reports.data.remote

import com.secure.resident.core.data.remote.baseUrl
import com.secure.resident.drawer.reports.domain.model.Report
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ReportsRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getUserReports(token: String , userId : String): List<Report> {
        val response = httpClient.get("${baseUrl}reports/user/$userId") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}