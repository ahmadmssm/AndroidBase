package com.ams.nwayaa.networking

interface ErrorHandler {
    fun onShowUnknownError()
    fun onShowError(errorMessage: String)
}