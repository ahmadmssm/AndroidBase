package com.ams.androiddevkit.utils.localeManager

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LegacyLocale: ILocale {

    @Suppress("DEPRECATION")
    override fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}