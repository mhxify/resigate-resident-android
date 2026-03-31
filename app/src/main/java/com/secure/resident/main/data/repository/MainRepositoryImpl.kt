package com.secure.resident.main.data.repository

import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.data.remote.MainRemoteDataSource
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.model.post.Post
import com.secure.resident.main.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainRemoteDataSource: MainRemoteDataSource
) : MainRepository {

    override suspend fun getUserGroups(token: String, userId: String): Result<List<GroupMessage>> {
        return try {
            val response = mainRemoteDataSource.getUserGroups(
                token = token ,
                userId = userId
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGroupMessage(token: String, groupId: String): Result<List<Message>> {
        return try {
            val response = mainRemoteDataSource.getGroupMessage(
                token = token ,
                groupId = groupId
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendMessage(
        request: SendMessageRequest,
        token: String
    ): Result<LiveMessageResponse> {
        return try {
            val response = mainRemoteDataSource.sendMessage(
                token = token ,
                request = request
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllPost(token: String): Result<List<Post>> {
        return try {
            val response = mainRemoteDataSource.getAllPost(
                token = token
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserPost(token: String, userId: String): Result<List<Post>> {
        return try {
            val response = mainRemoteDataSource.getUserPost(
                token = token ,
                userId = userId
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}