package com.secure.resident.guestrequest.domain.repository

import com.secure.resident.guestrequest.data.model.GuestRequest
import com.secure.resident.guestrequest.data.model.RequestGuestResponse

interface RequestGuestRepository {

    suspend fun requestGuest(token : String , request: GuestRequest) : Result<String>
}