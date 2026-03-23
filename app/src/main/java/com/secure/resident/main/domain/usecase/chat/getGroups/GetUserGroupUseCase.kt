package com.secure.resident.main.domain.usecase.chat.getGroups

import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class GetUserGroupUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        token : String ,
        userId : String
    ) : Result<List<GroupMessage>>{
        return mainRepository.getUserGroups(token , userId)
    }
}