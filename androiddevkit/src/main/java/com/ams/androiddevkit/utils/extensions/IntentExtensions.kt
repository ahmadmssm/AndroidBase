package com.ams.androiddevkit.utils.extensions

import android.content.Intent
import android.graphics.Bitmap

fun Intent.getBitmap(): Bitmap {
    return extras?.get("data") as Bitmap
}