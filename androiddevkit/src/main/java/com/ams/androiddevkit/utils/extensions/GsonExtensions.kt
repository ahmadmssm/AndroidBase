@file:Suppress("unused")

package com.ams.androiddevkit.utils.extensions

import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory

fun Gson.getConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create(this)
}