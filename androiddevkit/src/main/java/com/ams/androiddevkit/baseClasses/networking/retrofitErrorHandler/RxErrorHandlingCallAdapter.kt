package com.ams.androiddevkit.baseClasses.networking.retrofitErrorHandler

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.*
import java.io.IOException
import java.lang.reflect.Type

@Suppress("MemberVisibilityCanBePrivate", "unused")
internal class RxErrorHandlingCallAdapter<R>(private val retrofit: Retrofit,
                                             private val responseType: Type,
                                             val scheduler: Scheduler?,
                                             val isAsync: Boolean,
                                             private val isResult: Boolean,
                                             private val isBody: Boolean,
                                             private val isFlowable: Boolean,
                                             private val isSingle: Boolean,
                                             private val isMaybe: Boolean,
                                             private val isCompletable: Boolean): CallAdapter<R, Any> {
    override fun responseType(): Type {
        return responseType
    }

    @SuppressLint("CheckResult")
    override fun adapt(call: Call<R>): Any? {
        val responseObservable: Observable<Response<R>> = if (isAsync) CallEnqueueObservable(call) else CallExecuteObservable(call)
        var observable: Observable<*>
        observable = when {
            isResult -> {
                ResultObservable(responseObservable)
            }
            isBody -> {
                BodyObservable(responseObservable)
            }
            else -> {
                responseObservable
            }
        }
        if (scheduler != null) {
            observable = observable.subscribeOn(scheduler)
        }
        if (isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST).onErrorResumeNext { throwable: Throwable ->
                Flowable.error(asRetrofitException(throwable))
            }
        }
        if (isSingle) {
            return observable.singleOrError().onErrorResumeNext { throwable: Throwable ->
                Single.error(asRetrofitException(throwable))
            }
        }
        if (isMaybe) {
            return observable.singleElement().onErrorResumeNext { throwable: Throwable ->
                Maybe.error(asRetrofitException(throwable))
            }
        }
        return if (isCompletable) {
            observable.ignoreElements().onErrorResumeNext { throwable: Throwable ->
                Completable.error(asRetrofitException(throwable))
            }
        }
        else RxJavaPlugins.onAssembly(observable.onErrorResumeNext { throwable: Throwable ->
            Observable.error(asRetrofitException(throwable))
        })
    }

    private fun asRetrofitException(throwable: Throwable): RetrofitException {
        // We had non 200-399 range http status code
        if (throwable is HttpException) {
            val response = throwable.response()
            return RetrofitException.httpError(response?.raw()?.request?.url.toString(), response!!, retrofit)
        }

        // A network error happened
        if (throwable is IOException) {
            return RetrofitException.networkError(throwable)
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return RetrofitException.unexpectedError(throwable)
    }
}