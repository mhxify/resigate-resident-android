package com.secure.resident.auth.domain.usecase.forgot.verifyOtp

import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email : String ,
        otp : String
    ) : Result<Unit> {
        return authRepository.verifyOtp(email , otp)
    }
}