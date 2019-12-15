//package com.ams.androiddevkit.utils.connectivity.receiver;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import com.ams.androiddevkit.utils.connectivity.ConnectivityService;
//import com.ams.androiddevkit.utils.services.logging.LoggingService;
//
////  INSIDE APPLICATION APP CLASS ADD THIS
////  NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
////  registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
////  AND IN BASE PRESENTERS
////  addDisposable(RxBusMessages.subscribeToConnectivityChange(lifeCycle(), this,getLoggingService()));
//public class NetworkChangeReceiver extends BroadcastReceiver {
//
//    private final ConnectivityService connectivityService;
//    private final LoggingService loggingService;
//
//    public NetworkChangeReceiver(ConnectivityService connectivityService, LoggingService loggingService) {
//        super();
//        this.connectivityService = connectivityService;
//        this.loggingService = loggingService;
//        BootstrapRxConnectivityBus.getRxBus(connectivityService).send(new BootstrapIsOnline(connectivityService.haveActiveNetwork()));
//    }
//
//    @Override
//    public void onReceive(final Context context, final Intent intent) {
//        loggingService.v("NetworkChangeReceiver", "OnReceive: haveActiveNetwork:" + connectivityService.haveActiveNetwork());
//        BootstrapRxConnectivityBus.getRxBus(connectivityService).send(new BootstrapIsOnline(connectivityService.haveActiveNetwork()));
//    }
//}