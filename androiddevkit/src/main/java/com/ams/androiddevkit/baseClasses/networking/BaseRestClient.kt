package com.ams.androiddevkit.baseClasses.networking

abstract class BaseRestClient: RetrofitClient() {

    init {
        buildRetrofit()
    }

    // Seconds.
    override fun getDefaultTimeOut() = 10

    override fun isMockable() = false

    override fun isDebuggable() = true
}

