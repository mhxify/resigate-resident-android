package com.secure.resident.post.domain.usecase

import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.data.model.CreatePostResponse
import com.secure.resident.post.domain.repository.CreatePostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val createPostRepository: CreatePostRepository
) {
    suspend operator fun invoke(
        token : String ,
        request: CreatePostRequest
    ) : Result<CreatePostResponse> {
        return createPostRepository.createPost(token , request)
    }
}