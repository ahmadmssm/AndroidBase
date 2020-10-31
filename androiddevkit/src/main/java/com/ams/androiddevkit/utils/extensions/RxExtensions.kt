@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.ams.androiddevkit.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.ReplaySubject

fun <T> Observable<T>.applyThreadingConfig(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyThreadingConfig(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.applyThreadingConfig(): Maybe<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.applyThreadingConfig(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applyThreadingConfig(): Completable {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

@Suppress("SpellCheckingInspection")
fun <T> LiveData<T>.toFlowable(lifecycleOwner: LifecycleOwner): Flowable<T> =
    Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, this))


fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Observable<T>.execute(compositeDisposable: CompositeDisposable, next: (T) -> Unit, complete: () -> Unit?, error: (Throwable) -> Unit?) {
    this.applyThreadingConfig()
        .subscribe( { next(it) }, { error(it) }, { complete() })
        .addTo(compositeDisposable)
}

fun <T> Flowable<T>.execute(compositeDisposable: CompositeDisposable, next: (T) -> Unit, complete: () -> Unit?, error: (Throwable) -> Unit?) {
    this.applyThreadingConfig()
        .subscribe( { next(it) }, { error(it) }, { complete() })
        .addTo(compositeDisposable)
}

fun <T> Maybe<T>.execute(compositeDisposable: CompositeDisposable, success: (T) -> Unit, error: (Throwable) -> Unit?) {
    this.applyThreadingConfig()
        .subscribe({ success(it) },{ error(it) })
        .addTo(compositeDisposable)
}

fun <T> Single<T>.execute(compositeDisposable: CompositeDisposable, success: (T) -> Unit, error: (Throwable) -> Unit?) {
    this.applyThreadingConfig()
        .subscribe({ success(it) },{ error(it) })
        .addTo(compositeDisposable)
}

fun Completable.execute(compositeDisposable: CompositeDisposable, success: () -> Unit, error: (Throwable) -> Unit?) {
    this.applyThreadingConfig()
        .subscribe({ success() },{ error(it) })
        .addTo(compositeDisposable)
}

fun <T> Observable<T>.execute(compositeDisposable: CompositeDisposable,
                              next: (T) -> Unit,
                              complete: () -> Unit?,
                              error: (Throwable) -> Unit?,
                              doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe( { next(it) }, { error(it) }, { complete() })
        .addTo(compositeDisposable)
}

fun <T> Flowable<T>.execute(compositeDisposable: CompositeDisposable,
                            next: (T) -> Unit,
                            complete: () -> Unit?,
                            error: (Throwable) -> Unit?,
                            doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe( { next(it) }, { error(it) }, { complete() })
        .addTo(compositeDisposable)
}

fun <T> Maybe<T>.execute(compositeDisposable: CompositeDisposable,
                         success: (T) -> Unit,
                         error: (Throwable) -> Unit?,
                         doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe({ success(it) },{ error(it) })
        .addTo(compositeDisposable)
}

fun <T> Single<T>.execute(compositeDisposable: CompositeDisposable,
                          success: (T) -> Unit,
                          error: (Throwable) -> Unit?,
                          doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe({ success(it) },{ error(it) })
        .addTo(compositeDisposable)
}

fun Completable.execute(compositeDisposable: CompositeDisposable,
                        success: () -> Unit,
                        error: (Throwable) -> Unit?,
                        doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe({ success() },{ error(it) })
        .addTo(compositeDisposable)
}

fun <T> ReplaySubject<T>.executeSubject(compositeDisposable: CompositeDisposable,
                                        next: (T) -> Unit,
                                        complete: () -> Unit?,
                                        error: (Throwable) -> Unit?,
                                        doFinally: () -> Unit) {
    this.applyThreadingConfig()
        .doFinally { doFinally() }
        .subscribe( { next(it) }, { error(it) }, { complete() })
        .addTo(compositeDisposable)
}

fun Disposable.disposeIfNot() {
    if (!isDisposed) dispose()
}
