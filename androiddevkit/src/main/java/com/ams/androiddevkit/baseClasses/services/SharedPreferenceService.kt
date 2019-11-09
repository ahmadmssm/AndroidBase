package com.ams.androiddevkit.baseClasses.services

import android.content.SharedPreferences

interface SharedPreferenceService {
    abstract fun saveString(key: String, value: String): Boolean
    abstract fun saveInt(key: String, value: Int): Boolean
    abstract fun saveLong(key: String, value: Long): Boolean
    abstract fun saveBoolean(key: String, value: Boolean?): Boolean
    abstract fun getString(key: String): String
    abstract fun getBoolean(key: String): Boolean?
    abstract fun getInt(key: String, fallback: Int): Int?
    abstract fun getInt(key: String): Int?
    abstract fun getLong(key: String): Long?
    abstract fun getLong(key: String, defaultValue: Long): Long?
    abstract fun containsKey(key: String): Boolean
    abstract fun getSharedPreferences(): SharedPreferences
    abstract fun removeKey(key: String): Boolean
    abstract fun clearAll()
    abstract fun saveStringSet(key: String, value: Set<String>): Boolean
    abstract fun getStringSet(key: String): Set<String>
    abstract fun removeAllKeys()
}