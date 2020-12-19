package com.ams.androiddevkit.utils.localeManager

import android.content.Context
import java.util.*

interface ILocale {
    fun updateResources(context: Context, locale: Locale): Context
    fun updateResources(context: Context, language: String): Context
    fun updateResources(context: Context, language: String, countryKey: String): Context {
        val locale = Locale(language, countryKey)
        return updateResources(context, locale)
    }
}