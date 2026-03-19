package com.secure.resident.auth.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.secure.resident.auth.domain.model.User

object AuthPrefs {

    private const val PREF_NAME = "auth_prefs"
    private const val KEY_TOKEN = "token"
    private const val KEY_EMAIL = "email"
    private const val KEY_FULL_NAME = "fullName"
    private const val KEY_IMAGE_URL = "imageUrl"
    private const val KEY_ROLE = "role"
    private const val KEY_USER_ID = "user_id"

    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginData(
        context: Context,
        token: String,
        userId: String,
        email: String ,
        fullName : String ,
        imageUrl : String  ,
        role : String
    ) {
        prefs(context).edit {
            putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_EMAIL, email)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_FULL_NAME , fullName)
                .putString(KEY_IMAGE_URL , imageUrl)
                .putString(KEY_ROLE , role)
        }
    }

    fun saveEmail(
        email: String ,
        context: Context
    ) {
        prefs(context).edit {
            putString(KEY_EMAIL, email)
        }
    }

    fun getEmail(context: Context) : String? {
        return prefs(context).getString(KEY_EMAIL , "")
    }

    fun getToken(context: Context): String? {
        return prefs(context).getString(KEY_TOKEN, null)
    }

    fun getUser(context: Context) : User {
        val userId = prefs(context).getString(KEY_USER_ID , "")
        val email = prefs(context).getString(KEY_EMAIL , "")
        val fullName = prefs(context).getString(KEY_FULL_NAME , "")
        val imageUrl = prefs(context).getString(KEY_IMAGE_URL , "")
        val role = prefs(context).getString(KEY_ROLE , "")

        return User(
            userId = userId ,
            email = email ,
            fullname = fullName ,
            imageUrl = imageUrl ,
            role = role
        )
    }


    fun isLoggedIn(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout(context: Context) {
        prefs(context).edit { clear() }
    }
}