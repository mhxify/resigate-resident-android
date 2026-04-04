package com.secure.resident.reserveFacility.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object ReserveFacilityPrefs {

    private const val PREF_NAME = "reserve_facility_prefs"
    private const val KEY_FACILITY_ID = "facility_id"

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveFacilityId(
        facilityId: String ,
        context: Context
    ) {
        prefs(context).edit {
            putString(KEY_FACILITY_ID, facilityId)
        }
    }

    fun getFacilityId(context: Context) : String? {
        return prefs(context).getString(KEY_FACILITY_ID , "")
    }

}