package com.secure.resident.drawer.facilities.data.repository

import com.secure.resident.drawer.facilities.data.remote.ReservationRemoteDataSource
import com.secure.resident.drawer.facilities.domain.model.Reservation
import com.secure.resident.drawer.facilities.domain.repository.ReservationRepository
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationRemoteDataSource: ReservationRemoteDataSource
) : ReservationRepository {

    override suspend fun getUserReservation(
        token: String,
        userId: String
    ): Result<List<Reservation>> {
        return try {
            val response = reservationRemoteDataSource.getUserReservation(token , userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}