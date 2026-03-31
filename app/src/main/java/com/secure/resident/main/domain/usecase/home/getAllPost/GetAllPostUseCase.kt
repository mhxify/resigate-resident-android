package com.secure.resident.main.domain.usecase.home.getAllPost

import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class GetAllPostUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        token : String
    ) : Result<List<Post>> {
        return mainRepository.getAllPost(token)
    }
}