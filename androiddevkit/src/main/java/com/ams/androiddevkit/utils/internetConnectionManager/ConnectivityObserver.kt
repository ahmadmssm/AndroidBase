package com.ams.androiddevkit.utils.internetConnectionManager

import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ams.androiddevkit.utils.extensions.distinctUntilChanged
import com.ams.androiddevkit.utils.kotlinLiveBus.core.KotlinLiveBus

@Suppress("unused")
object ConnectivityObserver {

    private const val OBSERVATION_KEY = "com.ams.androidDevKit.networkObserver"
    private const val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    // Call in application class onCreate method
    fun start(context: Context) {
        val intentFilter = IntentFilter(CONNECTIVITY_ACTION)
        context.registerReceiver(NetworkChangeReceiver(), intentFilter)
    }

    @Suppress("unused")
    fun stop(context: Context) {
        context.unregisterReceiver(NetworkChangeReceiver())
    }

    fun postConnectionStatus(connectionType: NetworkConnection) {
        KotlinLiveBus.postStickyLiveEventValue(OBSERVATION_KEY, connectionType)
    }

    // Call in base activity onCreate method
    fun startNetworkChangeListener(lifecycleOwner: LifecycleOwner,
                                   shouldEnableConnectivityMonitoring: Boolean = true,
                                   onNetworkConnectedAction: (internetConnectionType: InternetConnectionType) -> Unit,
                                   onNetworkDisconnectedAction: () -> Unit) {
        if (shouldEnableConnectivityMonitoring) {
            KotlinLiveBus
                .getStickyLiveEvent(OBSERVATION_KEY, NetworkConnection::class.java)
                .distinctUntilChanged()
                .observe(lifecycleOwner, Observer { networkConnection ->
                    val connectionType = networkConnection.internetConnectionType
                    if (connectionType == InternetConnectionType.WIFI ||
                        connectionType == InternetConnectionType.MOBILE ||
                        connectionType == InternetConnectionType.ETHERNET) onNetworkConnectedAction(connectionType)
                    else onNetworkDisconnectedAction()
                })
        }
    }

    // Call in base activity onDestroy method
    fun stopNetworkChangeListener() {
        KotlinLiveBus.removeEvent(OBSERVATION_KEY)
    }
}