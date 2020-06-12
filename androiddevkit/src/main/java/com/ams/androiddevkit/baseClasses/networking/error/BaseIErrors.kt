package com.ams.androiddevkit.baseClasses.networking.error

interface BaseIErrors {
    fun onShowUnknownError(throwable: Throwable)
    fun onShowNetworkError(throwable: Throwable)
    fun onShowError(errorMessage: String)
}