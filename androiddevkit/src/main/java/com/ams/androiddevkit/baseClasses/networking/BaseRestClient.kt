package com.ams.androiddevkit.baseClasses.networking

import com.ams.androiddevkit.baseClasses.networking.interceptor.BaseRefreshTokenInterceptor

abstract class BaseRestClient: RetrofitClient() {

    init {
        buildRetrofit()
    }

    // Seconds.
    override fun getDefaultTimeOut() = 10

    override fun isMockable() = false

    override fun isDebuggable() = true

    override fun getRefreshTokenInterceptor(): BaseRefreshTokenInterceptor<*>? = null
}

