@file:Suppress("PropertyName")

package com.ams.androiddevkit.baseClasses.globalKeys

import android.Manifest

open class BasePermissionsKeys {
    // Storage
    val WRITE_TO_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val READ_FROM_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    // Camera
    val CAMERA = Manifest.permission.CAMERA
}