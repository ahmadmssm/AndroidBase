package com.ams.androiddevkit.utils.services.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.ams.androiddevkit.utils.sharedPreference.AndroidSharedPreferences

class SharedPrefServiceImp(context: Context): SharedPrefService {

    companion object { private const val SHARED_PREFS_CONNECTION = "SHARED_PREF_CONNECTION" }

    private var androidPreferences = AndroidSharedPreferences.with(context, SHARED_PREFS_CONNECTION)

    override var sharedPreferences: SharedPreferences = androidPreferences.preferences

    override fun saveString(key: String, value: String): Boolean {
        androidPreferences.addString(key, value).save()
        return true
    }

    override fun saveInt(key: String, value: Int): Boolean {
        androidPreferences.addInt(key, value).save()
        return true
    }

    override fun saveLong(key: String, value: Long): Boolean {
        androidPreferences.addLong(key, value).save()
        return true
    }

    override fun saveBoolean(key: String, value: Boolean): Boolean {
        androidPreferences.addBoolean(key, value).save()
        return true
    }

    override fun saveStringSet(key: String, value: Set<String?>?): Boolean {
        androidPreferences.addStringSet(key, value).save()
        return true
    }

    override fun saveObject(key: String, value: Any): Boolean {
        androidPreferences.addObject(key, value).save()
        return true
    }

    override fun getString(key: String): String? {
        return androidPreferences.getString(key)
    }

    override fun getBoolean(key: String): Boolean? {
        return androidPreferences.getBoolean(key)
    }

    override fun getInt(key: String, fallback: Int): Int? {
        return androidPreferences.getInt(key, fallback)
    }

    override fun getInt(key: String): Int? {
        return androidPreferences.getInt(key)
    }

    override fun getLong(key: String): Long? {
        return androidPreferences.getLong(key)
    }

    override fun getLong(key: String, defaultValue: Long): Long? {
        return androidPreferences.getLong(key, defaultValue)
    }

    override fun containsKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun removeKey(key: String): Boolean {
        androidPreferences.remove(key)
        return true
    }

    override fun clearAll() {
       androidPreferences.clearAll()
    }

    override fun getStringSet(key: String): Set<String?>? {
        return androidPreferences.getStringSet(key)
    }

    override fun <T> getObject(key: String, clazz: Class<T>): T? {
        return androidPreferences.getObject(key, clazz)
    }

    override fun removeAllKeys() {
        clearAll()
    }
}