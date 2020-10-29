package com.ams.androiddevkit.baseClasses.networking

import android.content.Context
import okhttp3.Cache
import java.io.File

@Suppress("unused")
open class OkHttpUtils(protected val context: Context) {

    open fun getETagCache(cacheSize: Long): Cache {
        return Cache(File(context.cacheDir, "ok-http-cache"), cacheSize)
    }

    open fun getETagCache(): Cache {
        // 10 MiB
        val cacheSize = 10 * 1024 * 1024.toLong()
        return getETagCache(cacheSize)
    }
}