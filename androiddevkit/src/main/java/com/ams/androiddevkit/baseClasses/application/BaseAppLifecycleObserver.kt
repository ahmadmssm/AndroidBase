package com.ams.androiddevkit.baseClasses.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

@Suppress("unused")
open class BaseAppLifecycleObserver: LifecycleObserver {

    // ON_CREATE will be dispatched once
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onAppCreated() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onAppResume() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onAppEnterForeground() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onAppPaused() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onAppEnterBackground() {}

    // ON_DESTROY will never be dispatched
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onAppTerminated() {}
}