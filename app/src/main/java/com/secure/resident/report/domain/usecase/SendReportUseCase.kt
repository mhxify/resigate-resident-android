package com.secure.resident.report.domain.usecase

import com.secure.resident.report.data.model.SendReportRequest
import com.secure.resident.report.data.model.SendReportResponse
import com.secure.resident.report.domain.repository.SendReportRepository
import javax.inject.Inject

class SendReportUseCase @Inject constructor(
    private val sendReportRepository: SendReportRepository
) {
    suspend operator fun invoke(
        token : String ,
        request : SendReportRequest
    ) : Result<SendReportResponse> {
        return sendReportRepository.sendReport(token , request)
    }
}