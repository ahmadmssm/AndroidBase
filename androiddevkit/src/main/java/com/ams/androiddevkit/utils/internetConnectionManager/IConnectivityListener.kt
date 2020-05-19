package com.ams.androiddevkit.utils.internetConnectionManager

import android.net.ConnectivityManager

interface IConnectivityListener {

    fun listenForNetworkChanges(connectivityManager: ConnectivityManager)

    fun posConnectedToWifi() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                InternetConnectionType.WIFI
            )
        )
    }

    fun posConnectedToMobile() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                InternetConnectionType.MOBILE
            )
        )
    }

    fun posConnectedToEthernet() {
        ConnectivityObserver.postConnectionStatus(
            NetworkConnection(
                InternetConnectionType.ETHERNET
            )
        )
    }

    fun postNotConnected() { ConnectivityObserver.postConnectionStatus(
        NetworkConnection(
        InternetConnectionType.NOT_CONNECTED)
    ) }
}