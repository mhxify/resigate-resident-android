package com.secure.resident.reserveFacility.domain.repository

import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.data.model.ReserveFacilityResponse
import com.secure.resident.reserveFacility.domain.model.Facility

interface FacilityRepository {

    suspend fun getFacilities(token : String) : Result<List<Facility>>

    suspend fun postReservation(token : String , request: ReserveFacilityRequest) : Result<ReserveFacilityResponse>

}
