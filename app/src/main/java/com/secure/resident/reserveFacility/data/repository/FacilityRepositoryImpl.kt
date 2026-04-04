package com.secure.resident.reserveFacility.data.repository

import com.secure.resident.reserveFacility.data.model.ReserveFacilityRequest
import com.secure.resident.reserveFacility.data.model.ReserveFacilityResponse
import com.secure.resident.reserveFacility.data.remote.FacilityRemoteDataSource
import com.secure.resident.reserveFacility.domain.model.Facility
import com.secure.resident.reserveFacility.domain.repository.FacilityRepository
import javax.inject.Inject

class FacilityRepositoryImpl @Inject constructor(
    private val facilityRemoteDataSource: FacilityRemoteDataSource
) : FacilityRepository {

    override suspend fun getFacilities(token: String): Result<List<Facility>> {
        return try {
            val response = facilityRemoteDataSource.getFacilities(token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postReservation(
        token: String,
        request: ReserveFacilityRequest
    ): Result<ReserveFacilityResponse> {
        return try {
            val response = facilityRemoteDataSource.postReservation(token , request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}