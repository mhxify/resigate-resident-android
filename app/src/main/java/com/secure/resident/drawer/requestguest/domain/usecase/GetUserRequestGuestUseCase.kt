package com.secure.resident.drawer.requestguest.domain.usecase

import com.secure.resident.drawer.requestguest.domain.model.RequestGuest
import com.secure.resident.drawer.requestguest.domain.repository.RequestGuestRepository
import javax.inject.Inject

class GetUserRequestGuestUseCase @Inject constructor(
    private val requestGuestRepository: RequestGuestRepository
) {
    suspend operator fun invoke(
        token : String ,
        userId : String
    ) : Result<List<RequestGuest>> {
        return requestGuestRepository.getUserRequestGuest(token , userId)
    }
}