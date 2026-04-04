package com.secure.resident.reserveFacility.domain.model

import com.secure.resident.reserveFacility.domain.enums.FacilitiesStatus
import kotlinx.serialization.Serializable

@Serializable
data class Facility(
    val capacity: Int,
    val facilityId: String,
    val imageUrl: String,
    val name: String ,
    val facilityStatus : FacilitiesStatus
)