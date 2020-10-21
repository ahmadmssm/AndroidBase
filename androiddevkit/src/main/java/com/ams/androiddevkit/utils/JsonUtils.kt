package com.ams.androiddevkit.utils

import android.content.Context

open class JsonUtils {

    // Use with Android only
    open fun loadJsonFileAsStringFromResourcesFolder(context: Context, fileName: String): String? {
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
    open fun loadJsonFileAsStringFromResourcesFolder(fileName: String): String? {
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