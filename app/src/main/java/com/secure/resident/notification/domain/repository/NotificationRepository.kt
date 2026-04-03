package com.secure.resident.notification.domain.repository

import com.secure.resident.notification.domain.model.Notification

interface NotificationRepository {

    suspend fun getUserNotification(token : String , userId : String) : Result<List<Notification>>

    suspend fun markNotificationAsRead(token : String , notificationId : String) : Result<Unit>
}