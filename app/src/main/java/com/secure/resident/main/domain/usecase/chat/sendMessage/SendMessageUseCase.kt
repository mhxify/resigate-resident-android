package com.secure.resident.main.domain.usecase.chat.sendMessage

import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        request : SendMessageRequest ,
        token : String
    ) : Result<LiveMessageResponse> {
        return mainRepository.sendMessage(request , token)
    }
}