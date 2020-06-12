@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.ams.androiddevkit.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.ams.androiddevkit.utils.liveDataUtils.LifecycleConvert
import com.ams.androiddevkit.utils.rx.ObservableTakeBetween
import com.ams.androiddevkit.utils.rx.SingleFilterSingle
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

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
    return RxJavaPlugins.onAssembly(
        SingleFilterSingle<T>(
            this,
            predicate
        )
    )
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


fun <T, U> Observable<T>.takeBetween(other: Observable<U>, start: U, end: U): Observable<T> {
    return RxJavaPlugins.onAssembly<T>(
        ObservableTakeBetween<T, U>(
            this,
            other,
            start,
            end
        )
    )
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

fun Disposable.disposeIfNot() {
    if (!isDisposed) dispose()
}
