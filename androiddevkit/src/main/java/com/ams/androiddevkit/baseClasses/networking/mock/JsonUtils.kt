package com.ams.androiddevkit.baseClasses.networking.mock

import android.content.Context

object JsonUtils {

    // Use with Android only
    fun loadJsonFileAsStringFromResourcesFolder(context: Context, fileName: String): String? {
        val jsonString: String?
        try {
            val inputStream = context.assets.open("$fileName.json")
            jsonString = inputStream.bufferedReader().use{ it.readText() }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return jsonString
    }

    // Use with unit testing only
    fun loadJsonFileAsStringFromResourcesFolder(fileName: String): String? {
        val jsonString: String?
        try {
            val inputStream = this.javaClass.classLoader!!.getResourceAsStream("$fileName.json")
            jsonString = inputStream.bufferedReader().use{ it.readText() }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return jsonString
    }
}