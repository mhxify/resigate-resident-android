package com.secure.resident.drawer.requestguest.domain.repository

import com.secure.resident.drawer.requestguest.domain.model.RequestGuest

interface RequestGuestRepository {

    suspend fun getUserRequestGuest(token : String , userId : String) : Result<List<RequestGuest>>

}