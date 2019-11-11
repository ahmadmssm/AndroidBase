package com.ams.androiddevkit.baseClasses

import android.app.Activity
import android.content.Intent

@Suppress("unused")
abstract class BaseNavigator {
    protected abstract fun getActivityContext(): Activity
    protected inline fun <reified Activity>getActivityIntent() = Intent(getActivityContext(), Activity::class.java)
}