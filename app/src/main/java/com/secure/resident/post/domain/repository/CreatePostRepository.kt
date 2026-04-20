package com.secure.resident.post.domain.repository

import com.secure.resident.core.presentation.state.ResultState
import com.secure.resident.post.data.model.CreatePostRequest
import com.secure.resident.post.data.model.CreatePostResponse

interface CreatePostRepository {

    suspend fun createPost(token : String , request: CreatePostRequest) : Result<CreatePostResponse>
}