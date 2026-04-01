package com.secure.resident.drawer.facilities.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val createdAt: String,
    val endTime: String,
    val numberOfGuests: Int,
    val reservationDate: String,
    val reservationId: String,
    val startTime: String,
    val status: String,
    val userId: String
)