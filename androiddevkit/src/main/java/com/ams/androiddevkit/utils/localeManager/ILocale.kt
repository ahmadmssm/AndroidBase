package com.ams.androiddevkit.utils.localeManager

import android.content.Context

interface ILocale {
    fun updateResources(context: Context, language: String): Context
}