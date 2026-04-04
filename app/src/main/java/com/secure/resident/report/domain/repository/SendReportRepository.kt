package com.secure.resident.report.domain.repository

import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.data.model.SendReportResponse

interface SendReportRepository {

    suspend fun sendReport(token : String , request : SendReportRequest) : Result<SendReportResponse>

}