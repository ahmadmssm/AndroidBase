package com.ams.androiddevkit.utils.kotlinLiveBus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Base class for all `LiveEvent` classes. Removes the observer
 * before adding it to prevent duplicate observers.
 */
open class BaseLiveEvent<T>: MutableLiveData<T>() {

    protected var mPendingObserve: Boolean = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Let the same observer to register once
        mPendingObserve = true
        removeObserver(observer)
        mPendingObserve = false
        super.observe(owner, observer)
    }
}
