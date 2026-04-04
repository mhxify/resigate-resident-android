package com.secure.resident.reserveFacility.domain.model

data class ReservationValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)