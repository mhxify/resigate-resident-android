package com.secure.resident.drawer.incidents.data.repository

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
}