package com.ams.androiddevkit.utils.internetConnectionManager

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

open class NewConnectivityListener : IConnectivityListener {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun listenForNetworkChanges(connectivityManager: ConnectivityManager) {
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                postStatusForConnectionType(connectivityManager)
            }

            override fun onLost(network: Network) {
                postStatusForConnectionType(connectivityManager)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    open fun postStatusForConnectionType(connectivityManager: ConnectivityManager) {
        val wifi = NetworkCapabilities.TRANSPORT_WIFI
        val mobile = NetworkCapabilities.TRANSPORT_CELLULAR
        val etherNet = NetworkCapabilities.TRANSPORT_CELLULAR
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            @Suppress("MemberVisibilityCanBePrivate")
            when {
                it.hasTransport(wifi) -> posConnectedToWifi()
                it.hasTransport(mobile) -> posConnectedToMobile()
                it.hasTransport(etherNet) -> posConnectedToEthernet()
                else -> postNotConnected()
            }
        } ?: run { postNotConnected() }
    }
}