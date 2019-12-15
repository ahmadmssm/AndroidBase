package com.ams.androiddevkit.utils.extensions

import android.content.Context
import android.content.SharedPreferences
import com.ams.androiddevkit.utils.services.sharedpreferences.SharedPrefServiceImp
import com.ams.androiddevkit.utils.sharedPreference.reactiveSharedPreference.LiveSharedPreferences

fun SharedPreferences.getLiveSharedPreference(context: Context): LiveSharedPreferences {
    val sharedPreference = SharedPrefServiceImp(context).sharedPreferences
    return LiveSharedPreferences(
        sharedPreference
    )
}
