package com.secure.resident.auth.data.model.updatefcm

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFcm(
    val fcmToken: String
)