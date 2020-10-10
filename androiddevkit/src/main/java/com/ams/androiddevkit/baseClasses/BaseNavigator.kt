package com.ams.androiddevkit.baseClasses

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings

@Suppress("unused")
abstract class BaseNavigator {

    protected abstract fun getCurrentActivity(): Activity

    protected inline fun <reified Activity>getActivityIntent() = Intent(getCurrentActivity(), Activity::class.java)

    open fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + getCurrentActivity().packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        getCurrentActivity().startActivity(intent)
    }

    open fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        getCurrentActivity().startActivity(intent)
    }

    // Remember to add the phone number with country code.
    open fun openWhatsAppChat(phoneNumber: String, failAction: () -> Unit?) {
        try {
            val pm: PackageManager = getCurrentActivity().packageManager
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=${phoneNumber}"
            intent.data = Uri.parse(url)
            getCurrentActivity().startActivity(intent)
        }
        catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            failAction.invoke()
        }
    }
}