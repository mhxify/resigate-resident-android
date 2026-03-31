package com.secure.resident.main.domain.usecase.home.getUserPost

import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class GetUserPostUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        token : String ,
        userId : String
    ) : Result <List<Post>>{
        return mainRepository.getUserPost(token , userId)
    }
}