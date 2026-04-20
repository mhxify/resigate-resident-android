package com.secure.resident.main.data.model.createGroup

import com.google.zxing.common.BitArray
import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupMessageRequest(
    val userId : String ,
    val groupName : String ,
    val memberIds : List<String> ,
    val image : ByteArray? = null
)