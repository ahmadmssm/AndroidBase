package com.ams.androiddevkit.utils.services.gsonService

import com.ams.androiddevkit.utils.gson.Date2JsonAdapter
import com.ams.androiddevkit.utils.gson.Json2DateAdapter
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.Date

@Suppress("ProtectedInFinal", "unused", "MemberVisibilityCanBePrivate")
open class GsonServiceImpl: GsonService {

    override fun getGsonConverter(serverDateFormat: String, targetDateFormat: String): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter(targetDateFormat))
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter(serverDateFormat))
            .create()
    }

    override fun getGsonConverter(serverDateFormat: String): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter())
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter(serverDateFormat))
            .create()
    }

    override fun getGsonConverter(): Gson {
        return this
            .getGsonBuilder()
            .registerTypeAdapter(Date::class.java, Json2DateAdapter())
            .registerTypeAdapter(Date::class.java, Date2JsonAdapter())
            .create()
    }

    protected open fun getFieldNamingPolicy(): FieldNamingPolicy? {
        return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
    }

    protected open fun serializeNulls() = true

    override fun getGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .setVersion(1.0)
        if (serializeNulls())
            gsonBuilder.serializeNulls()
        if (getFieldNamingPolicy() != null)
            gsonBuilder.setFieldNamingPolicy(getFieldNamingPolicy())
        return gsonBuilder
    }
}
