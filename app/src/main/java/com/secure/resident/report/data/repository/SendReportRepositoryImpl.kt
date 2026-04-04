package com.secure.resident.report.data.repository

import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.data.model.SendReportResponse
import com.secure.resident.report.data.remote.SendReportRemoteDataSource
import com.secure.resident.report.domain.repository.SendReportRepository
import javax.inject.Inject

class SendReportRepositoryImpl @Inject constructor(
    private val sendReportRemoteDataSource: SendReportRemoteDataSource
) : SendReportRepository {

    override suspend fun sendReport(
        token: String,
        request: SendReportRequest
    ): Result<SendReportResponse> {
        return try {
            val response = sendReportRemoteDataSource.sendReport(token , request)
            Result.success(response)
        }catch (e : Exception) {
            Result.failure(e)
        }
    }
}