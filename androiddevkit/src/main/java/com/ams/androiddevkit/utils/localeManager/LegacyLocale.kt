package com.ams.androiddevkit.utils.localeManager

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LegacyLocale: ILocale {

    override fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        return updateResources(context, locale)
    }

    @Suppress("DEPRECATION")
    override fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}