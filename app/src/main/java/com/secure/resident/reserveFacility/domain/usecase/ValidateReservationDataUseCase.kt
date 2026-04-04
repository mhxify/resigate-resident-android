package com.secure.resident.reserveFacility.domain.usecase

import com.secure.resident.reserveFacility.domain.model.ReservationValidationResult
import javax.inject.Inject

class ValidateReservationDataUseCase @Inject constructor() {

    operator fun invoke(
        reservationDate: String,
        startTime: String,
        endTime: String,
        numberOfGuest: String
    ): ReservationValidationResult {

        return when {
            reservationDate.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please select reservation date"
                )
            }

            startTime.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please select start time"
                )
            }

            endTime.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please select end time"
                )
            }

            numberOfGuest.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please enter number of guests"
                )
            }

            numberOfGuest.toIntOrNull() == null -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Number of guests must be a valid number"
                )
            }

            numberOfGuest.toInt() <= 0 -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Number of guests must be greater than 0"
                )
            }

            !isEndTimeAfterStartTime(startTime, endTime) -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "End time must be after start time"
                )
            }

            else -> {
                ReservationValidationResult(
                    isValid = true
                )
            }
        }
    }

    private fun isEndTimeAfterStartTime(
        startTime: String,
        endTime: String
    ): Boolean {
        return try {
            val startParts = startTime.split(":")
            val endParts = endTime.split(":")

            val startHour = startParts[0].toInt()
            val startMinute = startParts[1].toInt()

            val endHour = endParts[0].toInt()
            val endMinute = endParts[1].toInt()

            val startTotalMinutes = startHour * 60 + startMinute
            val endTotalMinutes = endHour * 60 + endMinute

            endTotalMinutes > startTotalMinutes
        } catch (e: Exception) {
            false
        }
    }
}