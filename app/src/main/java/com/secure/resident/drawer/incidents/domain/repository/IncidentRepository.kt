package com.secure.resident.drawer.incidents.domain.repository

import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.data.model.CreateIncidentResponse
import com.secure.resident.drawer.incidents.domain.model.Incident

interface IncidentRepository {

    suspend fun getUserIncident(token : String , userId : String) : Result<List<Incident>>

    suspend fun createIncident(token: String , request: CreateIncidentRequest) : Result<CreateIncidentResponse>

}