package com.ams.androiddevkit.utils.di

import android.content.Context
import android.content.res.Resources
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

@Suppress("unused")
open class ResourceProvider(private val context: Context) {
    open fun getString(@StringRes id: Int) = context.getString(id)
    open fun getString(@StringRes id: Int, vararg  formatArgs: Any) = context.getString(id)
    open fun getColor(@ColorRes id: Int) = ContextCompat.getColor(context, id)
    open fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)
    open fun getFont(@FontRes id: Int) = ResourcesCompat.getFont(context, id)
    open fun getDimension(@DimenRes id: Int) = context.resources.getDimension(id)
    open fun getResources(): Resources = context.resources
}