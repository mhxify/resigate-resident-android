package com.secure.resident.main.domain.repository

import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.data.model.message.SendMessageRequest
import com.secure.resident.main.domain.model.group.GroupMessage
import com.secure.resident.main.domain.model.message.Message
import com.secure.resident.main.domain.model.post.Post

interface MainRepository {
    suspend fun getUserGroups(token : String , userId : String) : Result<List<GroupMessage>>

    suspend fun getGroupMessage(token : String , groupId : String) : Result<List<Message>>

    suspend fun sendMessage(request : SendMessageRequest , token: String) : Result<LiveMessageResponse>

    suspend fun getUserPost( token: String , userId : String) : Result<List<Post>>

    suspend fun getAllPost( token: String ) : Result<List<Post>>


}