package com.secure.resident.main.domain.usecase.chat.getGroupMessage

import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class GetGroupMessageUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        token : String ,
        groupId : String
    ) : Result<List<Message>>{
        return mainRepository.getGroupMessage(token , groupId)
    }
}