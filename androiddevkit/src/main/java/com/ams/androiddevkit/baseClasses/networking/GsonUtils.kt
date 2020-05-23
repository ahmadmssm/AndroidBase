package com.ams.androiddevkit.baseClasses.networking

import com.ams.androiddevkit.utils.gson.Date2JsonAdapter
import com.ams.androiddevkit.utils.gson.Json2DateAdapter
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.Date

@Suppress("ProtectedInFinal", "unused", "MemberVisibilityCanBePrivate")
open class GsonUtils {

    open fun getCustomGsonConverter(serverDateFormat: String, targetDateFormat: String): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter(targetDateFormat))
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter(serverDateFormat))
            .create()
    }

    open fun getCustomGsonConverter(serverDateFormat: String): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter())
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter(serverDateFormat))
            .create()
    }

    open fun getCustomGsonConverter(): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter())
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter())
            .create()
    }

    protected fun getFieldNamingPolicy(): FieldNamingPolicy {
        return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
    }

    open fun getGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .setFieldNamingPolicy(getFieldNamingPolicy())
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .setVersion(1.0)
    }
}
