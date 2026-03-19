package com.secure.resident.auth.domain.usecase.login

import com.secure.resident.auth.data.model.login.response.LoginDtoResponse
import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<LoginDtoResponse> {
        return authRepository.login(
            email = email,
            password = password
        )
    }

}