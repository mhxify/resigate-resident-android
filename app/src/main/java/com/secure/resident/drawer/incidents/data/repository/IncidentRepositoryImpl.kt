package com.secure.resident.drawer.incidents.data.repository

import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.data.model.CreateIncidentResponse
import com.secure.resident.drawer.incidents.data.remote.IncidentRemoteDataSource
import com.secure.resident.drawer.incidents.domain.model.Incident
import com.secure.resident.drawer.incidents.domain.repository.IncidentRepository
import javax.inject.Inject

class IncidentRepositoryImpl @Inject constructor(
    private val incidentRemoteDataSource: IncidentRemoteDataSource
) : IncidentRepository {

    override suspend fun getUserIncident(token: String, userId: String): Result<List<Incident>> {
        return try {
            val response = incidentRemoteDataSource.getUserIncident(token , userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createIncident(
        token: String,
        request: CreateIncidentRequest
    ): Result<CreateIncidentResponse> {
        return try {
            val response = incidentRemoteDataSource.reportIncident(token , request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}