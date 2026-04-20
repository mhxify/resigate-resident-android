package com.secure.resident.guestrequest.domain.usecase

import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.data.model.RequestGuestResponse
import com.secure.resident.guestrequest.domain.repository.RequestGuestRepository
import javax.inject.Inject

class RequestGuestUseCase @Inject constructor(
    private val requestGuestRepository: RequestGuestRepository
) {
    suspend operator fun invoke(
        token : String ,
        request : GuestRequest
    ) : Result<String> {
        return requestGuestRepository.requestGuest(token , request)
    }
}