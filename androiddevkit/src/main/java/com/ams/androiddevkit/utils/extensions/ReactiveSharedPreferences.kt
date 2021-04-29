package com.ams.androiddevkit.utils.extensions

import android.content.Context
import android.content.SharedPreferences
import com.ams.androiddevkit.utils.services.sharedpreferences.SharedPrefServiceImpl
import com.ams.androiddevkit.utils.sharedPreference.reactiveSharedPreference.LiveSharedPreferences

fun SharedPreferences.getLiveSharedPreference(context: Context): LiveSharedPreferences {
    val sharedPreference = SharedPrefServiceImpl(context).sharedPreferences
    return LiveSharedPreferences(sharedPreference)
}
