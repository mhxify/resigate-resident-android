package com.secure.resident.main.domain.usecase.chat.createGroup

import com.secure.resident.main.data.model.createGroup.CreateGroupMessageRequest
import com.secure.resident.main.data.model.createGroup.CreateGroupMessageResponse
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        token : String ,
        request : CreateGroupMessageRequest
    ) : Result<CreateGroupMessageResponse> {
        return mainRepository.createGroupMessage(token , request)
    }
}