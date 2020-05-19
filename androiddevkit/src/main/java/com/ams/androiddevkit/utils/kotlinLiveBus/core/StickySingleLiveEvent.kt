package com.ams.androiddevkit.utils.kotlinLiveBus.core

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.ams.androiddevkit.utils.kotlinLiveBus.utils.logWarningOnlyInDebug
import java.util.concurrent.atomic.AtomicBoolean

internal class StickySingleLiveEvent<T> : BaseLiveEvent<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            logWarningOnlyInDebug("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    fun isConsumed() : Boolean = !mPending.get()
}