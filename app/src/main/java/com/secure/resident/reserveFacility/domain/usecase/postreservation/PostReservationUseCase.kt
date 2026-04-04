package com.secure.resident.reserveFacility.domain.usecase.postreservation

import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.data.model.ReserveFacilityResponse
import com.secure.resident.reserveFacility.domain.repository.FacilityRepository
import javax.inject.Inject

class PostReservationUseCase @Inject constructor(
    private val facilityRepository: FacilityRepository
) {

    suspend operator fun invoke(
        token : String ,
        request: ReserveFacilityRequest
    ) : Result<ReserveFacilityResponse> {
        return facilityRepository.postReservation(token , request)
    }
}