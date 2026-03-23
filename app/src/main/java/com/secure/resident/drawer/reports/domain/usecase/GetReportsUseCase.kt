package com.secure.resident.drawer.reports.domain.usecase

import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.repository.ReportsRepository
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val repository: ReportsRepository
) {
    suspend operator fun invoke(token: String): Result<List<Report>> =
        repository.getReports(token)
}