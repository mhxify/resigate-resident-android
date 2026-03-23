package com.secure.resident.drawer.reports.domain

import com.secure.resident.drawer.reports.domain.Report
import com.secure.resident.drawer.reports.domain.ReportsRepository
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(
    private val repository: ReportsRepository
) {
    suspend operator fun invoke(): Result<List<Report>> {
        return runCatching { repository.getReports() }
    }
}