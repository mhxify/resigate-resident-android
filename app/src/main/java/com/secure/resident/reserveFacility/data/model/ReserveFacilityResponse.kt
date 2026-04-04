package com.secure.resident.reserveFacility.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReserveFacilityResponse(

    @SerialName("reservationId")
    val reservationId : String
)
