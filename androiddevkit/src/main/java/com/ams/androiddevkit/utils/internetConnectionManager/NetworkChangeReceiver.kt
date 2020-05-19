package com.ams.androiddevkit.utils.internetConnectionManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build

open class NetworkChangeReceiver: BroadcastReceiver() {

    protected open val connectivityListener: IConnectivityListener by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NewConnectivityListener()
        }
        else {
            LegacyConnectivityListener()
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let { connectivityListener.listenForNetworkChanges(it) }
    }
}
