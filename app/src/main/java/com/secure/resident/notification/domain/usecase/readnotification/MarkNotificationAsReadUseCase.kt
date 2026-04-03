package com.secure.resident.notification.domain.usecase.readnotification

import com.secure.resident.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkNotificationAsReadUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        token : String ,
        notificationId : String
    ) : Result<Unit>{
        return notificationRepository.markNotificationAsRead(token , notificationId)
    }
}