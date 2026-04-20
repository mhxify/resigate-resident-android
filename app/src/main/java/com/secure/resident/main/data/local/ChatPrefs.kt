package com.secure.resident.main.data.local

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import com.secure.resident.main.domain.model.group.GroupMessage

object ChatPrefs {

    private const val PREF_NAME = "chat_prefs"

    private const val KEY_GROUP_ID = "group_id"
    private const val KEY_GROUP_NAME = "group_name"
    private const val KEY_GROUP_IMAGE = "group_image"
    private const val KEY_GROUP_CREATED_DATE = "group_created_date"

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setGroupInfo(
        groupId : String ,
        groupName : String ,
        groupImage : String ,
        groupCreatedAt : String ,
        context: Context
    ) {
        prefs(context).edit {
            putString(KEY_GROUP_ID , groupId)
            putString(KEY_GROUP_IMAGE , groupImage)
            putString(KEY_GROUP_NAME , groupName)
            putString(KEY_GROUP_CREATED_DATE , groupCreatedAt)
        }
    }



    fun getGroupInfo(context: Context) : GroupMessage {
        val groupId = prefs(context).getString(KEY_GROUP_ID , "")
        val groupName = prefs(context).getString(KEY_GROUP_NAME , "")
        val groupImage = prefs(context).getString(KEY_GROUP_IMAGE , "")
        val groupCreatedAt = prefs(context).getString(KEY_GROUP_CREATED_DATE , "")

        return GroupMessage(
            groupId = groupId ,
            groupName = groupName ,
            imageUrl = groupImage ,
            createdAt = groupCreatedAt
        )
    }


    private const val KEY_GROUP_NAME_KEY = "group_name_key"
    private const val KEY_IMAGE_URI = "group_image_uri"

    fun setGroupCreateInfo(
        groupName: String,
        imageUri: Uri,
        context: Context
    ) {
        prefs(context).edit {
            putString(KEY_GROUP_NAME_KEY, groupName)
            putString(KEY_IMAGE_URI, imageUri.toString())
        }
    }

    fun getGroupImageUri(context: Context): Uri? {
        val uriString = prefs(context).getString(KEY_IMAGE_URI, null)
        return uriString?.let { Uri.parse(it) }
    }

    fun getGroupName(context: Context): String? {
        return prefs(context).getString(KEY_GROUP_NAME_KEY, null)
    }
}