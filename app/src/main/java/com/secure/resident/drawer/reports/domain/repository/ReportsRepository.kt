package com.secure.resident.drawer.reports.domain.repository

import com.secure.resident.drawer.reports.domain.model.Report

interface ReportsRepository {
    suspend fun getUserReports(token: String , userId : String): Result<List<Report>>
}