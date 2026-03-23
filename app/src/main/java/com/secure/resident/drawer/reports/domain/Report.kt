package com.secure.resident.drawer.reports.domain

data class Report(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val status: String
)