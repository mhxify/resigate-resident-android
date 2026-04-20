package com.secure.resident.guestrequest.domain.usecase

import com.secure.resident.reserveFacility.domain.model.ReservationValidationResult
import javax.inject.Inject

class ValidationGuestRequestDataUseCase @Inject constructor(){
    operator fun invoke(
        guestEmail: String,
        startTime: String,
        endTime: String,
        fullName : String ,
        date : String
    ): ReservationValidationResult {

        return when {
            guestEmail.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please enter the guest email"
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


            !isEndTimeAfterStartTime(startTime, endTime) -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "End time must be after start time"
                )
            }

            fullName.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please enter the guest full name"
                )
            }

            date.isBlank() -> {
                ReservationValidationResult(
                    isValid = false,
                    errorMessage = "Please select the request guest date"
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