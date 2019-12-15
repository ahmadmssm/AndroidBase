package com.ams.androiddevkit.utils.connectivity

interface ConnectivityService {
    fun haveNetworkConnection(): Boolean
    fun haveActiveNetwork(): Boolean
}