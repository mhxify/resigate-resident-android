package com.secure.resident.drawer.reports.data.repository

import com.secure.resident.drawer.reports.data.remote.ReportsRemoteDataSource
import com.secure.resident.drawer.reports.domain.model.Report
import com.secure.resident.drawer.reports.domain.repository.ReportsRepository
import javax.inject.Inject


class ReportsRepositoryImpl @Inject constructor(
    private val reportsRemoteDataSource: ReportsRemoteDataSource
) : ReportsRepository {

    override suspend fun getUserReports(token: String , userId : String): Result<List<Report>> {
        return try {
            val response = reportsRemoteDataSource.getUserReports(token , userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}