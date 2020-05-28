package com.ams.androiddevkit.utils.kotlinLiveBus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * `StickyLiveEvent` class which is a special `BaseLiveEvent` that gets
 * removed only if `removeEvent(..)` gets explicitly called.
 */
class StickyLiveEvent<T> : BaseLiveEvent<T>() {

    private var mConsumeCount = 0

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        mConsumeCount++
    }

    fun getConsumeCount(): Int = mConsumeCount

    override fun setValue(value: T) {
        super.setValue(value)
        mConsumeCount = 0
    }
}