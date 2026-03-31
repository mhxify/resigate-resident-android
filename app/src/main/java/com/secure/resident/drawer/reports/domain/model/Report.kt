package com.secure.resident.drawer.reports.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Report(
    val content : String ,
    val sentAt : String ,
    val imageUrl : String ? ,
    val userId : String ,
    val repliedAt : String ? ,
    val adminReply : String ?
)