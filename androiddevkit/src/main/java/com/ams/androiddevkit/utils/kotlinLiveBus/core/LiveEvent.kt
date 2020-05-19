package com.ams.androiddevkit.utils.kotlinLiveBus.core

import androidx.lifecycle.Observer

/**
 * `LiveEvent` class which is a special `LiveEventBase` that gets
 * removed when there is no observer left but it'll be delivered
 * to at least one observer before it gets removed.
 */
class LiveEvent<T> : BaseLiveEvent<T>() {

    override fun removeObserver(observer: Observer<in T>) {
        super.removeObserver(observer)
        // Remove from bus if there are no observer left
        if (!mPendingObserve && !hasObservers()) {
            KotlinLiveBus.removeEvent(this)
        }
    }
}
