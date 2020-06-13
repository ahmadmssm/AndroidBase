package com.ams.androiddevkit.baseClasses.networking

abstract class BaseRestClient: RetrofitClient() {

    init {
        buildRetrofit()
    }

    override fun isMockable() = false

    override fun isDebuggable() = true
}

