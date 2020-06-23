package com.ams.androiddevkit.utils.services.gsonService

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface GsonService {
    fun getGsonBuilder(): GsonBuilder
    fun getGsonConverter(): Gson
    fun getGsonConverter(serverDateFormat: String): Gson
    fun getGsonConverter(serverDateFormat: String, targetDateFormat: String): Gson
}