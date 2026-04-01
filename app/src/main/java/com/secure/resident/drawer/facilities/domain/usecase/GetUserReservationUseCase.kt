package com.secure.resident.drawer.facilities.domain.usecase

import com.secure.resident.drawer.facilities.domain.model.Reservation
import com.secure.resident.drawer.facilities.domain.repository.ReservationRepository
import javax.inject.Inject

class GetUserReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(
        token : String ,
        userId : String
    ) : Result<List<Reservation>> {
        return reservationRepository.getUserReservation(token , userId)
    }
}