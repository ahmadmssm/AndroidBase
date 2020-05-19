package com.ams.androiddevkit.utils.internetConnectionManager

import android.net.ConnectivityManager

interface IConnectivityListener {

    fun listenForNetworkChanges(connectivityManager: ConnectivityManager)

    fun posConnectedToWifi() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                ConnectionType.WIFI
            )
        )
    }

    fun posConnectedToMobile() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                ConnectionType.MOBILE
            )
        )
    }

    fun posConnectedToEthernet() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                ConnectionType.ETHERNET
            )
        )
    }

    fun postNotConnected() { ConnectivityObserver.postConnectionStatus(
        NetworkConnection(
        ConnectionType.NOT_CONNECTED)
    ) }
}