package com.ams.androiddevkit.utils.localeManager

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class NewLocale: ILocale {

    @TargetApi(Build.VERSION_CODES.N)
    override fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        return updateResources(context, locale)
    }

    override fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}