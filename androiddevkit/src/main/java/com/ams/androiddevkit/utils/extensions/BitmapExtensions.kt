package com.ams.androiddevkit.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun Bitmap.toImageFile(context: Context, filename: String): File {
    val file = File(context.cacheDir, filename)
    file.createNewFile()
    // Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
    val bitmapData: ByteArray = bos.toByteArray()
    // Write the bytes in file
    val fos = FileOutputStream(file)
    fos.write(bitmapData)
    fos.flush()
    fos.close()
    return file
}