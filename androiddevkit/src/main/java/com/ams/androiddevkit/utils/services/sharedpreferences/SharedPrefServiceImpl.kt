package com.ams.androiddevkit.utils.services.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.ams.androiddevkit.utils.sharedPreference.AndroidSharedPreferences

open class SharedPrefServiceImpl(context: Context, preferenceFileName: String = "SHARED_PREF_CONNECTION"): SharedPrefService {

    @Suppress("MemberVisibilityCanBePrivate")
    protected var androidPreferences = AndroidSharedPreferences.with(context, preferenceFileName)

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


    override fun getBoolean(key: String): Boolean {
        return androidPreferences.getBoolean(key, false)
    }

    override fun getBoolean(key: String, fallbackValue: Boolean): Boolean {
        return androidPreferences.getBoolean(key, fallbackValue)
    }

    override fun getInt(key: String, fallbackValue: Int): Int? {
        return androidPreferences.getInt(key, fallbackValue)
    }

    override fun getInt(key: String): Int? {
        return androidPreferences.getInt(key)
    }

    override fun getLong(key: String): Long? {
        return androidPreferences.getLong(key)
    }

    override fun getLong(key: String, fallbackValue: Long): Long? {
        return androidPreferences.getLong(key, fallbackValue)
    }

    override fun containsKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun removeValueWithKey(key: String): Boolean {
        androidPreferences.remove(key).save()
        return true
    }

    override fun clearAll() {
       androidPreferences.clearAll().save()
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