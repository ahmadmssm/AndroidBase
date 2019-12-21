@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.ams.androiddevkit.utils.extensions

import androidx.lifecycle.LifecycleOwner
import com.ams.androiddevkit.utils.LifecycleConvert
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Predicate
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.fuseable.HasUpstreamObservableSource
import io.reactivex.observers.SerializedObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

fun <T> Observable<T>.applyThreadingConfig(): Observable<T> {
    return this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyThreadingConfig(): Single<T> {
    return this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.applyThreadingConfig(): Maybe<T> {
    return this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.applyThreadingConfig(): Flowable<T> {
    return this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applyThreadingConfig(): Completable {
    return this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.filterOrNever(predicate: Predicate<in T>): Single<T> {
    return RxJavaPlugins.onAssembly(SingleFilterSingle<T>(this, predicate))
}

private class SingleFilterSingle<T>(private val source: SingleSource<T>, private val predicate: Predicate<in T>): Single<T>() {

    override fun subscribeActual(observer: SingleObserver<in T>) {
        source.subscribe(FilterSingleObserver(observer, predicate))
    }

    private class FilterSingleObserver<T>(
        val actual: SingleObserver<in T>,
        val predicate: Predicate<in T>
    ) : SingleObserver<T>, Disposable {

        var d: Disposable? = null

        override fun dispose() {
            val d = this.d
            this.d = DisposableHelper.DISPOSED
            d?.dispose()
        }

        override fun isDisposed(): Boolean {
            return d?.isDisposed != false
        }

        override fun onSubscribe(d: Disposable) {
            if (DisposableHelper.validate(this.d, d)) {
                this.d = d
                actual.onSubscribe(this)
            }
        }

        override fun onSuccess(value: T) {
            val b: Boolean

            try {
                b = predicate.test(value)
            } catch (ex: Throwable) {
                Exceptions.throwIfFatal(ex)
                actual.onError(ex)
                return
            }

            if (b) {
                actual.onSuccess(value)
            }
            //else will be Single.never()
        }

        override fun onError(e: Throwable) {
            actual.onError(e)
        }
    }
}

fun <T, U> Observable<T>.takeBetween(other: Observable<U>, start: U, end: U): Observable<T> {
    return RxJavaPlugins.onAssembly<T>(ObservableTakeBetween<T, U>(this, other, start, end))
}

class ObservableTakeBetween<T, U>(
    private val source: ObservableSource<T>,
    private val other: ObservableSource<out U>,
    private val start: U,
    private val end: U
) : Observable<T>(), HasUpstreamObservableSource<T> {

    override fun source(): ObservableSource<T> = source

    override fun subscribeActual(child: Observer<in T>) {
        val serial = SerializedObserver(child)

        val frc = ArrayCompositeDisposable(2)

        val tbs = TakeBetweenObserver(serial, frc)

        child.onSubscribe(frc)

        other.subscribe(TakeBetween(frc, serial, start, end))

        source.subscribe(tbs)
    }

    private class TakeBetweenObserver<T>(
        val actual: Observer<in T>,
        val frc: ArrayCompositeDisposable
    ) : AtomicBoolean(), Observer<T> {

        lateinit var s: Disposable

        override fun onSubscribe(s: Disposable) {
            if (DisposableHelper.validate(this.s, s)) {
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

inline fun <reified T> Observable<T>.bindLifecycle(owner: LifecycleOwner): Observable<T> =
    LifecycleConvert.bindLifecycle(this, owner)

inline fun <reified T> Single<T>.bindLifecycle(owner: LifecycleOwner): Maybe<T> =
    LifecycleConvert.bindLifecycle(this, owner)

inline fun <reified T> Single<T>.bindLifecycleWithError(owner: LifecycleOwner): Single<T> =
    LifecycleConvert.bindLifecycleWithError(this, owner)

inline fun <reified T> Maybe<T>.bindLifecycle(owner: LifecycleOwner): Maybe<T> =
    LifecycleConvert.bindLifecycle(this, owner)

inline fun <reified T> Flowable<T>.bindLifecycle(owner: LifecycleOwner): Flowable<T> =
    LifecycleConvert.bindLifecycle(this, owner)

inline fun Completable.bindLifecycle(owner: LifecycleOwner): Completable =
    LifecycleConvert.bindLifecycle(this, owner)

inline fun Completable.bindLifecycleWithError(owner: LifecycleOwner): Completable =
    LifecycleConvert.bindLifecycleWithError(this, owner)