package com.secure.resident.reserveFacility.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReserveFacilityRequest(
    val endTime: String,
    val facilityId: String,
    val numberOfGuests: Int,
    val reservationDate: String,
    val startTime: String,
    val userId: String
)