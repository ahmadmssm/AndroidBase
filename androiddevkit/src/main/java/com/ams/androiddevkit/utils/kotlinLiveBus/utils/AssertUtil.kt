package com.ams.khdmaApp.common.utils.kotlinLiveBus.utils

import android.os.Looper

fun assertMainThread(methodName: String) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        throw LiveBusException("Cannot invoke " + methodName + " on a background"
                + " thread")
    }
}
