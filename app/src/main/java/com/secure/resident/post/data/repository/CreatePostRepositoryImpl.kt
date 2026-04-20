package com.secure.resident.post.data.repository

import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.data.model.CreatePostResponse
import com.secure.resident.post.data.remote.CreatePostApi
import com.secure.resident.post.domain.repository.CreatePostRepository
import javax.inject.Inject

class CreatePostRepositoryImpl @Inject constructor(
    private val createPostApi: CreatePostApi
) : CreatePostRepository {

    override suspend fun createPost(
        token: String,
        request: CreatePostRequest
    ): Result<CreatePostResponse> {
        return try {
            val response = createPostApi.createPost(token , request)
            Result.success(response)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}