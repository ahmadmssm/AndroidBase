package com.ams.androiddevkit.utils.gson

import android.annotation.SuppressLint
import android.provider.Settings
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

open class Date2JsonAdapter: JsonSerializer<Date?> {

    protected var format: String? = null

    constructor()

    constructor(format: String?) {
        this.format = format
    }

    override fun serialize(date: Date?, type: Type, jsonSerializationContext: JsonSerializationContext): JsonElement {
        // Set the date format to the default mobile date format
        if (format.isNullOrBlank())
            format = Settings.System.DATE_FORMAT
        val formatter = SimpleDateFormat(format!!, Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        val dateFormatAsString = formatter.format(date)
        return JsonPrimitive(dateFormatAsString)
    }
}