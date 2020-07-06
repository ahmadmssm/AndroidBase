package com.ams.androiddevkit.utils.extensions

import okhttp3.Response
import java.net.HttpURLConnection

fun Response.notModified(): Boolean {
    return (this.isSuccessful &&
            this.cacheResponse?.networkResponse != null &&
            this.cacheResponse?.networkResponse?.code == HttpURLConnection.HTTP_NOT_MODIFIED)
}