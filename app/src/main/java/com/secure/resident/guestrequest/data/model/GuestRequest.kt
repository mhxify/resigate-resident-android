package com.secure.resident.guestrequest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GuestRequest(
    val endTime: String,
    val guestEmail: String,
    val startTime: String,
    val userId: String ,
    val guestFullName : String ,
    val guestDate : String
)