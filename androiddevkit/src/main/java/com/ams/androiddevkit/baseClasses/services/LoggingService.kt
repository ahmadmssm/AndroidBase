package com.ams.androiddevkit.baseClasses.services

interface LoggingService {
    abstract fun e(tag: String, msg: String)
    abstract fun i(tag: String, msg: String)
    abstract fun d(tag: String, msg: String)
    abstract fun w(tag: String, msg: String)
    abstract fun e(tag: String, msg: String, ex: Throwable)
    abstract fun v(tag: String, msg: String)
    abstract fun stackTrack(throwable: Throwable)
    abstract fun stackTrack(throwable: Throwable, force: Boolean)
    abstract fun logUser(userId: String, userName: String)
    abstract fun logUser(userId: Long, userName: String)
    abstract fun longInfo(tag: String, str: String)
}
