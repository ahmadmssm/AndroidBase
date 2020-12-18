package com.ams.androiddevkit.utils.services.serialization

import retrofit2.Converter
import java.io.IOException

interface SerializationService {
    @Throws(IOException::class)
    fun <T> fromJson(json: String, jsonObjectClass: Class<T>): T
    fun <T> toJson(t: T, jsonObjectClass: Class<T>): String
    fun <T> toJson(item: T): String
    fun <T> listOfObjectsFrom(string: String, clazz: Class<Array<T>>): List<T>
    fun <T> listFrom(string: String): List<T>
    fun <T> stringFrom(list: List<T>): String
    fun getRetrofitJsonConverterFactory(): Converter.Factory
}
