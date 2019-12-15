package com.ams.androiddevkit.utils.services.logging;

public interface LoggingService {
    void e(String tag, String msg);
    void i(String tag, String msg);
    void d(String tag, String msg);
    void w(String tag, String msg);
    void e(String tag, String msg, Throwable ex);
    void v(String tag, String msg);
    void stackTrack(Throwable throwable);
    void stackTrack(Throwable throwable, boolean force);
    void logUser(String userId, String userName);
    void logUser(long userId, String userName);
    void longInfo(String tag, String str);
}
