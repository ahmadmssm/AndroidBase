package com.ams.androiddevkit.utils.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.fuseable.HasUpstreamObservableSource
import io.reactivex.observers.SerializedObserver
import java.util.concurrent.atomic.AtomicBoolean

class ObservableTakeBetween<T, U>(
    private val source: ObservableSource<T>,
    private val other: ObservableSource<out U>,
    private val start: U,
    private val end: U
) : Observable<T>(),
    HasUpstreamObservableSource<T> {

    override fun source(): ObservableSource<T> = source

    override fun subscribeActual(child: Observer<in T>) {
        val serial = SerializedObserver(child)

        val frc = ArrayCompositeDisposable(2)

        val tbs =
            TakeBetweenObserver(
                serial,
                frc
            )

        child.onSubscribe(frc)

        other.subscribe(TakeBetween(frc, serial, start, end))

        source.subscribe(tbs)
    }

    private class TakeBetweenObserver<T>(
        val actual: Observer<in T>,
        val frc: ArrayCompositeDisposable
    ) : AtomicBoolean(),
        Observer<T> {

        lateinit var s: Disposable

        override fun onSubscribe(s: Disposable) {
            if (DisposableHelper.validate(
                    this.s,
                    s
                )
            ) {
                this.s = s
                frc.setResource(0, s)
            }
        }

        override fun onNext(t: T) {
            actual.onNext(t)
        }

        override fun onError(t: Throwable) {
            frc.dispose()
            actual.onError(t)
        }

        override fun onComplete() {
            frc.dispose()
            actual.onComplete()
        }

        companion object {
            private const val serialVersionUID = 2345678463753345L
        }
    }

    private inner class TakeBetween(
        val frc: ArrayCompositeDisposable,
        val serial: SerializedObserver<T>,
        private val start: U,
        private val end: U
    ) : Observer<U> {

        val isActive = AtomicBoolean(false)

        override fun onSubscribe(s: Disposable) {
            frc.setResource(1, s)
        }

        override fun onNext(t: U) {
            frc.dispose()
            serial.onComplete()
        }

        override fun onError(t: Throwable) {
            frc.dispose()
            serial.onError(t)
        }

        override fun onComplete() {
            frc.dispose()
            serial.onComplete()
        }
    }
}