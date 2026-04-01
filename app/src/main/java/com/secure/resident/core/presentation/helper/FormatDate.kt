package com.secure.resident.core.presentation.helper

fun formatDate(date: String): String {
    return try {
        date.take(10)
    } catch (e: Exception) {
        date
    }
}