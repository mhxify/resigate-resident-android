package com.secure.resident.drawer.incidents.domain.usecase

import com.secure.resident.drawer.incidents.domain.model.Incident
import com.secure.resident.drawer.incidents.domain.repository.IncidentRepository
import javax.inject.Inject

class GetUserIncidentUseCase @Inject constructor(
    private val incidentRepository: IncidentRepository
) {
    suspend operator fun invoke(token : String , userId : String ) : Result<List<Incident>> {
        return incidentRepository.getUserIncident(token , userId)
    }
}