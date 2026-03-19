package com.secure.resident.auth.domain.usecase.forgot.sendOtp

import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email : String
    ) : Result<Unit> {
        return authRepository.sendOtp(email)
    }
}