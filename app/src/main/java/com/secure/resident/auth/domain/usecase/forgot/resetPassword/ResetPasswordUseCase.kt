package com.secure.resident.auth.domain.usecase.forgot.resetPassword

import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject


class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email : String ,
        password : String
    ) : Result<Unit> {
        return authRepository.resetPassword(email , password)
    }
}