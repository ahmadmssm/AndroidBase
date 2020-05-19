@file:Suppress("DEPRECATION")

package com.ams.androiddevkit.utils.internetConnectionManager

import android.net.ConnectivityManager
import android.net.NetworkInfo

open class LegacyConnectivityListener: IConnectivityListener {

    override fun listenForNetworkChanges(connectivityManager: ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = networkInfo?.isConnectedOrConnecting == false
        val wifi = ConnectivityManager.TYPE_WIFI
        val mobile = ConnectivityManager.TYPE_MOBILE
        if (isConnected) {
            if (networkInfo?.type == wifi) posConnectedToWifi()
            if (networkInfo?.type == mobile) posConnectedToMobile()
        }
        else {
            postNotConnected()
        }
    }
}