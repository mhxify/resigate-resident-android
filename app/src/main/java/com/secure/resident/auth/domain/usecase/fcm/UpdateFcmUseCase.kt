package com.secure.resident.auth.domain.usecase.fcm

import com.secure.resident.auth.data.model.updatefcm.UpdateFcm
import com.secure.resident.auth.domain.repository.AuthRepository
import javax.inject.Inject

class UpdateFcmUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        request : UpdateFcm ,
        token : String
    ) : Result<Unit> {
        return authRepository.updateFcm(request , token)
    }
}