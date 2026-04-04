package com.secure.resident.reserveFacility.domain.usecase.getFacility

import com.secure.resident.reserveFacility.domain.model.Facility
import com.secure.resident.reserveFacility.domain.repository.FacilityRepository
import javax.inject.Inject

class GetFacilityUseCase @Inject constructor(
    private val facilityRepository: FacilityRepository
) {

    suspend operator fun invoke(
        token : String
    ) : Result<List<Facility>> {
        return facilityRepository.getFacilities(token)
    }
}