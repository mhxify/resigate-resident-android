package com.secure.resident.drawer.reports.domain.usecase

import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.repository.ReportsRepository
import javax.inject.Inject

class GetUserReportsUseCase @Inject constructor(
    private val repository: ReportsRepository
) {
    suspend operator fun invoke(token: String , userId : String): Result<List<Report>> =
        repository.getUserReports(token , userId)
}