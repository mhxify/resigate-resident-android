package com.secure.resident.drawer.reports.data.repository

import com.secure.resident.drawer.reports.data.remote.ReportsRemoteDataSource
import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.repository.ReportsRepository
import javax.inject.Inject


class ReportsRepositoryImpl @Inject constructor(
    private val reportsRemoteDataSource: ReportsRemoteDataSource
) : ReportsRepository {

    override suspend fun getReports(token: String): Result<List<Report>> {
        return try {
            val response = reportsRemoteDataSource.getReports(token = token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}