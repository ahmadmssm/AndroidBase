package com.ams.androiddevkit.baseClasses.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Cache
import java.io.File

@Suppress("unused")
open class RestUtils(private val context: Context) {

    open fun getETagCache(cacheSize: Long): Cache {
        return Cache(File(context.cacheDir, "ok-http-cache"), cacheSize)
    }

    open fun getETagCache(): Cache {
        // 10 MiB
        val cacheSize = 10 * 1024 * 1024.toLong()
        return getETagCache(cacheSize)
    }

    @Suppress("DEPRECATION")
    open fun isNetworkConnectionAvailable(): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    result = true
                }
            }
        }
        else {
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                // connected to the internet
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE ||
                    activeNetwork.type == ConnectivityManager.TYPE_VPN) {
                    result = true
                }
            }
        }
        return result
    }
}