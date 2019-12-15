package com.ams.androiddevkit.utils.services.logging;

import android.util.Log;

import com.ams.androiddevkit.BuildConfig;

/**
 * Abstraction layer on top of Android's Log
 * The only added value is the ENABLED flag,
 * set to false before publishing to disable logging.
 */

public class LoggingServiceImp implements LoggingService {

    private final static boolean ENABLED = BuildConfig.DEBUG;

    @Override
    public void i(String tag, String msg) {
        if (!ENABLED) return;
        Log.i(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        if (!ENABLED) return;
        Log.d(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        if (!ENABLED) return;
        Log.e(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        if (!ENABLED) return;
        Log.w(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable ex) {
        if (!ENABLED) return;
        Log.e(tag, msg, ex);
    }

    @Override
    public void v(String tag, String msg) {
        if (!ENABLED) return;
        Log.v(tag, msg);
    }

    @Override
    public void stackTrack(Throwable throwable) {
        if (!ENABLED) {
            return;
        }
        throwable.printStackTrace();
    }

    @Override
    public void stackTrack(Throwable throwable, boolean force) {
        if (!ENABLED) {
            return;
        }
        throwable.printStackTrace();
    }

    @Override
    public void logUser(String userId, String userName) {}

    @Override
    public void logUser(long userId, String userName) {}

    @Override
    public void longInfo(String tag, String str) {
        if (str.length() > 4000) {
            i(tag, str.substring(0, 4000));
            longInfo(tag, str.substring(4000));
        } else
            i(tag, str);
    }
}
