package com.secure.resident.notification.domain.usecase.getusernotification

import com.secure.resident.notification.domain.model.Notification
import com.secure.resident.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class GetUserNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        token : String ,
        userId : String
    ) : Result<List<Notification>> {
        return notificationRepository.getUserNotification(token , userId)
    }
}