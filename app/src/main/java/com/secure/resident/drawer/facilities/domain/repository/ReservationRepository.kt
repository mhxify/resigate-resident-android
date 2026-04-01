package com.secure.resident.drawer.facilities.domain.repository

import com.secure.resident.drawer.facilities.domain.model.Reservation

interface ReservationRepository {

    suspend fun getUserReservation(token : String , userId : String) : Result<List<Reservation>>

}