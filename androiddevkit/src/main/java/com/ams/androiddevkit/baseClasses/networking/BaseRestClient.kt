package com.ams.androiddevkit.baseClasses.networking

abstract class BaseRestClient<APIs> : RetrofitClient() {

    init {
        buildRetrofit()
    }

    // Seconds.
    override fun getDefaultTimeOut() = 10

    override fun isMockable() = false

    override fun isDebuggable() = true

    override fun getRefreshTokenInterceptor(): TokenInterceptor? = null
}

