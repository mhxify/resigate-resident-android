package com.secure.resident.main.presentation.helper

import android.content.Context
import android.net.Uri
import io.ktor.http.ContentType

fun getMimeType(context: Context, uri: Uri): String {
    return context.contentResolver.getType(uri) ?: "image/*"
}

fun mimeToContentType(mime: String): ContentType {
    return ContentType.parse(mime)
}