package com.secure.resident.drawer.incidents.domain.usecase

import com.secure.resident.drawer.incidents.data.model.CreateIncidentRequest
import com.secure.resident.drawer.incidents.data.model.CreateIncidentResponse
import com.secure.resident.drawer.incidents.domain.repository.IncidentRepository
import javax.inject.Inject

class CreateIncidentUseCase @Inject constructor(
    private val incidentRepository: IncidentRepository
) {

    suspend operator fun invoke(
        token : String ,
        request: CreateIncidentRequest
    ) : Result<CreateIncidentResponse> {
        return incidentRepository.createIncident(token , request)
    }
}