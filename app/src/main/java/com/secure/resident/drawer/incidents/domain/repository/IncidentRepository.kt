package com.secure.resident.drawer.incidents.domain.repository

import com.secure.resident.drawer.incidents.domain.model.Incident

interface IncidentRepository {

    suspend fun getUserIncident(token : String , userId : String) : Result<List<Incident>>

}