@file:Suppress("unused")

package com.ams.androiddevkit.utils.extensions

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.ams.androiddevkit.utils.rx.RxJavaUtils

/**
 * Emits the items after certain time, default is one second
 */
fun <T> LiveData<T>.debounce(duration: Long = 1000L) = MediatorLiveData<T>().also { mediatorLiveData ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mediatorLiveData.value = source.value
    }

    mediatorLiveData.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}

/**
 * Emits the items that are different from the last item
 */
fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> = MediatorLiveData<T>().also { mediatorLiveData ->
    mediatorLiveData.addSource(this, object : Observer<T> {

        private var isInitialized = false
        private var previousValue: T? = null

        override fun onChanged(newValue: T?) {
            val wasInitialized = isInitialized
            if (!isInitialized) {
                isInitialized = true
            }
            if(!wasInitialized || newValue != previousValue) {
                previousValue = newValue
                mediatorLiveData.postValue(newValue)
            }
        }
    })
}

/**
 * Skips the first n values
 */
fun <T> LiveData<T>.skip(count:Int): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var skippedCount = 0
    mutableLiveData.addSource(this) {
        if(skippedCount >= count) mutableLiveData.value = it
        skippedCount++
    }
    return mutableLiveData
}

inline fun <reified T> LiveData<T>.toObservable() = RxJavaUtils.toObservable(this)

inline fun <reified T> LiveData<T>.toObservableAllowNull(valueIfNull: T) =
    RxJavaUtils.toObservableAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toFlowable() = RxJavaUtils.toFlowable(this)

inline fun <reified T> LiveData<T>.toFlowableAllowNull(valueIfNull: T) =
    RxJavaUtils.toFlowableAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toSingle() = RxJavaUtils.toSingle(this)

inline fun <reified T> LiveData<T>.toSingleAllowNull(valueIfNull: T) =
    RxJavaUtils.toSingleAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toMaybe() = RxJavaUtils.toMaybe(this)

inline fun <reified T> LiveData<T>.toMaybeAllowNull(valueIfNull: T) =
    RxJavaUtils.toMaybeAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toCompletable() = RxJavaUtils.toCompletable(this)

inline fun <reified T> LiveData<T>.toCompletableAllowNull() = RxJavaUtils.toCompletableAllowNull(this)