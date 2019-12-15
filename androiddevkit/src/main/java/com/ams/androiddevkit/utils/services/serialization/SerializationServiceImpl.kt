package com.ams.androiddevkit.utils.services.serialization

import com.ams.androiddevkit.baseClasses.networking.GsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.ArrayList


class SerializationServiceImpl: SerializationService {

    private val gson: Gson by lazy { GsonUtils().getCustomGsonConverter() }

    @Throws(IOException::class)
    override fun <T>fromJson(json: String, jsonObjectClass: Class<T>): T {
        return gson.fromJson<T>(json, jsonObjectClass)
    }

    override fun <T>toJson(t: T, jsonObjectClass: Class<T>): String {
        return gson.toJson(t, jsonObjectClass)
    }

    override fun <T>listOfObjectsFrom(string: String, clazz: Class<Array<T>>): List<T> {
        val list: Array<T> = gson.fromJson(string, clazz)
        return listOf(*list)    }

    override fun <T>listFrom(string: String): ArrayList<T> {
        val listType = object: TypeToken<ArrayList<T>?>() {}.type
        return gson.fromJson(string, listType)    }

    override fun <T> stringFrom(list: ArrayList<T>): String {
        return gson.toJson(list)
    }
}
