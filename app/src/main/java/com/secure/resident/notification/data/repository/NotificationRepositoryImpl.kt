package com.secure.resident.notification.data.repository

import com.secure.resident.notification.data.remote.NotificationRemoteDataSource
import com.secure.resident.notification.domain.model.Notification
import com.secure.resident.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
) : NotificationRepository {

    override suspend fun getUserNotification(
        token: String,
        userId: String
    ): Result<List<Notification>> {
        return try {
            val response = notificationRemoteDataSource.getUserNotification(token , userId)
            Result.success(response)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markNotificationAsRead(token: String, notificationId: String) : Result<Unit> {
        return try {
            val response = notificationRemoteDataSource.markNotificationAsReaded(token , notificationId)
            Result.success(response)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

}