package com.secure.resident.auth.domain.usecase.getSystemUsers

import com.secure.resident.auth.domain.model.User
import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetSystemUsers @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        token : String
    ) : Result<List<User>>{
        return authRepository.getSystemUsers(token)
    }
}