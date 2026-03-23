package com.secure.resident.drawer.reports.domain

import com.secure.resident.drawer.reports.domain.Report

interface ReportsRepository {
    suspend fun getReports(): List<Report>
}