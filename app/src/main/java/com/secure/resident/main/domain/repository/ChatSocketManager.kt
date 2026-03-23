package com.secure.resident.main.domain.repository

import com.secure.resident.main.data.model.message.LiveMessageResponse

interface ChatSocketManager {
    fun connect(
        groupId: String,
        onMessageReceived: (LiveMessageResponse) -> Unit,
        onError: (String) -> Unit
    )
    fun disconnect()
}