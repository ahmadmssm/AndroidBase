package com.ams.androiddevkit.utils.services.sharedpreferences

import android.content.SharedPreferences

interface SharedPrefService {
    val sharedPreferences: SharedPreferences
    fun saveString(key: String, value: String): Boolean
    fun saveInt(key: String, value: Int): Boolean
    fun saveLong(key: String, value: Long): Boolean
    fun saveDouble(key: String, value: Double): Boolean
    fun saveFloat(key: String, value: Float): Boolean
    fun saveBoolean(key: String, value: Boolean): Boolean
    fun getString(key: String): String?
    fun getBoolean(key: String): Boolean
    fun getBoolean(key: String, fallbackValue: Boolean = false): Boolean
    fun getInt(key: String, fallbackValue: Int): Int?
    fun getInt(key: String): Int?
    fun getLong(key: String): Long?
    fun getLong(key: String, fallbackValue: Long): Long?
    fun getDouble(key: String, fallbackValue: Double): Double?
    fun getDouble(key: String): Double?
    fun getFloat(key: String, fallbackValue: Float): Float?
    fun getFloat(key: String): Float?
    fun containsKey(key: String): Boolean
    fun removeValueWithKey(key: String): Boolean
    fun clearAll()
    fun saveStringSet(key: String, value: Set<String?>?): Boolean
    fun getStringSet(key: String): Set<String?>?
    fun saveObject(key: String, value: Any): Boolean
    fun <T> getObject(key: String, clazz: Class<T>): T?
    fun removeAllKeys()
}