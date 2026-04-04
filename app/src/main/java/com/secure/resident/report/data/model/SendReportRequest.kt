package com.secure.resident.report.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendReportRequest(
    val userId : String ,
    val content : String ,
    val image : ByteArray
)
