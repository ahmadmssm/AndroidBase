package com.ams.androiddevkit.utils.localeManager

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.core.os.ConfigurationCompat
import java.util.*

// Ref: https://gunhansancar.com/change-language-programmatically-in-android/

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseLocaleManager {

    protected val legacyLocale by lazy { LegacyLocale() }
    protected val newLocale by lazy { NewLocale() }

    protected open fun localeSplitter() = "_"

    // Call in Application class attachBaseContext Super
    // Also call in BaseAppActivity class attachBaseContext Super (Only if needed -> Not needed in most cases)
    open fun attach(context: Context): Context {
        getSavedLocale(context)?.split(localeSplitter())?.let {
            return if(it.size == 2) {
                setAppLanguage(context, it.first(), it.last())
            }
            else {
                setAppLanguage(context, getFallbackLanguage())
            }
        } ?: run {
            return setAppLanguage(context, getFallbackLanguage())
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
        saveLocale(context, language, countryKey)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newLocale.updateResources(context, language, countryKey)
        }
        else legacyLocale.updateResources(context, language, countryKey)
    }

    open fun getCurrentAppLocale(context: Context): String? {
        return getSavedLocale(context)
    }

    open fun getDefaultLocale(): Locale = Locale.getDefault()

    open fun getDefaultLocale(context: Context) = Locale(getSavedLocale(context) ?: getFallbackLanguage())

    open fun getDeviceLocale(): Locale {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
    }

    open fun getDeviceLanguage(): String {
        return getDeviceLocale().language
    }

    abstract fun getFallbackLanguage(): String

    abstract fun getSavedLocale(context: Context): String?

    abstract fun saveLocale(context: Context, language: String)

    abstract fun saveLocale(context: Context, language: String, countryKey: String)

    open fun isRTL(): Boolean {
        return isLocaleRTL(Locale.getDefault())
    }

    open fun resetLocale(context: Context) {
        this.saveLocale(context, getFallbackLanguage())
    }

    open fun setLayoutDirection(activity: Activity) {
        val direction = if(isRTL())
            View.LAYOUT_DIRECTION_RTL
        else
            View.LAYOUT_DIRECTION_LTR
        activity.window.decorView.layoutDirection = direction
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun isLocaleRTL(locale: Locale): Boolean {
        val firstChar = locale.getDisplayName(locale)[0]
        val firstCharDirection = Character.getDirectionality(firstChar)
        val firstCondition = firstCharDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT
        val secondCondition= firstCharDirection == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC
        return firstCondition || secondCondition
    }

    //    Locale.getDefault().getLanguage()       ---> en
    //    Locale.getDefault().getISO3Language()   ---> eng
    //    Locale.getDefault().getCountry()        ---> US
    //    Locale.getDefault().getISO3Country()    ---> USA
    //    Locale.getDefault().getDisplayCountry() ---> United States
    //    Locale.getDefault().getDisplayName()    ---> English (United States)
    //    Locale.getDefault().toString()          ---> en_US
    //    Locale.getDefault().getDisplayLanguage()---> English
    //    Locale.getDefault().toLanguageTag()     ---> en-US
}