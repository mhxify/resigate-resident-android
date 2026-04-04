package com.secure.resident.core.presentation.helper

import android.content.Context
import android.net.Uri

fun Uri.toByteArray(context: Context): ByteArray? {
    return try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            inputStream.readBytes()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}