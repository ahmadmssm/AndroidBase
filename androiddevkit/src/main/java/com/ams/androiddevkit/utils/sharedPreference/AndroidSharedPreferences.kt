package com.ams.androiddevkit.utils.sharedPreference

// https://github.com/iamhabib/easy-preference?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4588

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object AndroidSharedPreferences {

    fun with(context: Context): Builder {
        return Builder(
            context
        )
    }

    fun with(context: Context, prefName: String): Builder {
        return Builder(
            context,
            prefName
        )
    }

    class Builder(context: Context, prefName: String = "AndroidSharedPreferences") {

        val preferences: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        private var editor: SharedPreferences.Editor = preferences.edit()

        fun addBoolean(key: String?, value: Boolean): Builder {
            editor.putBoolean(key, value)
            return this
        }

        fun addString(key: String?, value: String?): Builder {
            editor.putString(key, value)
            return this
        }

        fun addInt(key: String?, value: Int): Builder {
            editor.putInt(key, value)
            return this
        }

        @Suppress("unused")
        fun addFloat(key: String?, value: Float): Builder {
            editor.putFloat(key, value)
            return this
        }

        fun addLong(key: String?, value: Long): Builder {
            editor.putLong(key, value)
            return this
        }

        fun addStringSet(key: String?, value: Set<String?>?): Builder {
            editor.putStringSet(key, value)
            return this
        }

        fun addObject(key: String?, value: Any?): Builder {
            val gson = Gson()
            editor.putString(key, gson.toJson(value))
            return this
        }

        fun save(): Builder {
            editor.commit()
            return this
        }

        fun getBoolean(key: String?, defaultValue: Boolean = false): Boolean {
            return preferences.getBoolean(key, defaultValue)
        }

        fun getString(key: String?, defaultValue: String? = null): String? {
            return preferences.getString(key, defaultValue)
        }

        fun getInt(key: String?, defaultValue: Int = 0): Int {
            return preferences.getInt(key, defaultValue)
        }

        @Suppress("unused")
        fun getFloat(key: String?, defaultValue: Float = 0F): Float {
            return preferences.getFloat(key, defaultValue)
        }

        fun getLong(key: String?, defaultValue: Long = 0): Long {
            return preferences.getLong(key, defaultValue)
        }

        fun getStringSet(key: String?, defaultValue: Set<String?>? = null): Set<String>? {
            return preferences.getStringSet(key, defaultValue)
        }

        fun <GenericClass> getObject(key: String?, classType: Class<GenericClass>?): GenericClass? {
            if (preferences.contains(key)) {
                val gson = Gson()
                return gson.fromJson(preferences.getString(key, ""), classType)
            }
            return null
        }

        fun remove(key: String?): Builder {
            editor.remove(key)
            return this
        }

        fun clearAll(): Builder {
            editor.clear()
            return this
        }

        fun contains(key: String): Boolean {
            return preferences.contains(key)
        }
    }
}