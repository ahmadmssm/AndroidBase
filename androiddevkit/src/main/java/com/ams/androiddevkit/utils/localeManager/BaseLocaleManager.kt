package com.ams.androiddevkit.utils.localeManager

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.os.ConfigurationCompat
import java.util.*

// Ref: https://gunhansancar.com/change-language-programmatically-in-android/

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseLocaleManager {

    protected val legacyLocale by lazy { LegacyLocale() }
    protected val newLocale by lazy { NewLocale() }

    // Call in BaseAppActivity class attachBaseContext Super (Only if needed -> Not needed in most cases)
    open fun attach(context: Context): Context {
        val lang = getSavedLocale(context, Locale.getDefault().language)
        lang?.split("-")?.let {
            return setAppLanguage(context, it.first(), it.last())
        } ?: run {
            return setAppLanguage(context, lang!!)
        }
    }

    // Call in Application class attachBaseContext Super
    open fun attach(context: Context, defaultLanguage: String): Context {
        val lang = getSavedLocale(context, defaultLanguage)
        return setAppLanguage(context, lang!!)
    }

    // Call in Application class attachBaseContext Super
    open fun attach(context: Context, defaultLanguage: String, countryKey: String): Context {
        getSavedLocale(context, "$defaultLanguage-$countryKey")?.split("-")?.let {
            return if(it.size == 2) {
                setAppLanguage(context, it.first(), it.last())
            }
            else {
                setAppLanguage(context, it.toString())
            }
        } ?: run {
            return setAppLanguage(context, "$defaultLanguage-$countryKey")
        }
    }

    open fun setAppLanguage(context: Context, language: String): Context {
        saveLocale(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newLocale.updateResources(context, language)
        }
        else legacyLocale.updateResources(context, language)
    }

    open fun setAppLanguage(context: Context, language: String, countryKey: String): Context {
        saveLocale(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newLocale.updateResources(context, language, countryKey)
        }
        else legacyLocale.updateResources(context, language, countryKey)
    }

    open fun getCurrentAppLocale(context: Context): String? {
        return getSavedLocale(context, Locale.getDefault().language)
    }

    open fun getDefaultLocale(): Locale = Locale.getDefault()

    open fun getDeviceLocale(): Locale {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
    }

    open fun getDeviceLanguage(): String {
        return getDeviceLocale().language
    }

    abstract fun getSavedLocale(context: Context, defaultLanguage: String): String?

    abstract fun saveLocale(context: Context, language: String?)

    open fun isRTL(): Boolean {
        return isLocaleRTL(Locale.getDefault())
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun isLocaleRTL(locale: Locale): Boolean {
        val firstChar = locale.getDisplayName(locale)[0]
        val firstCharDirection = Character.getDirectionality(firstChar)
        val firstCondition = firstCharDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT
        val secondCondition= firstCharDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC
        return firstCondition || secondCondition
    }
}