package com.ams.androiddevkit.utils.services.serialization

import com.ams.androiddevkit.utils.extensions.getConverterFactory
import com.ams.androiddevkit.utils.services.gsonService.GsonService
import com.ams.androiddevkit.utils.services.gsonService.GsonServiceImpl
import com.google.gson.reflect.TypeToken
import retrofit2.Converter
import java.io.IOException

@Suppress("MemberVisibilityCanBePrivate")
open class GsonSerializationService(protected val gsonService: GsonService = GsonServiceImpl()): SerializationService {

    protected open val gsonConverter by lazy { gsonService.getGsonConverter() }

    @Throws(IOException::class)
    override fun <T>fromJson(json: String, jsonObjectClass: Class<T>): T {
        return gsonConverter.fromJson(json, jsonObjectClass)
    }

    override fun <T>toJson(t: T, jsonObjectClass: Class<T>): String {
        return gsonConverter.toJson(t, jsonObjectClass)
    }

    override fun <T> toJson(item: T): String {
        return gsonConverter.toJson(item)
    }

    override fun <T>listOfObjectsFrom(string: String, clazz: Class<Array<T>>): List<T> {
        val list: Array<T> = gsonConverter.fromJson(string, clazz)
        return listOf(*list)
    }

    override fun <T>listFrom(string: String): List<T> {
        val listType = object: TypeToken<List<T>?>() {}.type
        return gsonConverter.fromJson(string, listType)
    }

    override fun <T> stringFrom(list: List<T>): String {
        return gsonConverter.toJson(list)
    }

    override fun getRetrofitJsonConverterFactory(): Converter.Factory {
        return gsonService.getGsonConverter().getConverterFactory()
    }
}
