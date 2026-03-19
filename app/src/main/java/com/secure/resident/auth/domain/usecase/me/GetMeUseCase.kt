package com.secure.resident.auth.domain.usecase.me

import com.secure.resident.auth.domain.model.User
import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        token : String
    ) : Result<User> {
        return authRepository.getMe(token)
    }
}