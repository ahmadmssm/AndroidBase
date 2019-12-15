package com.ams.androiddevkit.baseClasses.networking.retrofitErrorHandler

import io.reactivex.*
import retrofit2.*
import retrofit2.adapter.rxjava2.Result
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("unused")
class RxErrorHandlingCallAdapterFactory(private val scheduler: Scheduler?,
                                        private val isAsync: Boolean): CallAdapter.Factory() {

    companion object {
        fun create() : CallAdapter.Factory = RxErrorHandlingCallAdapterFactory(null, false)
    }

    override fun get(returnType: Type,
                     annotations: Array<Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == Completable::class.java) {
            // Completable is not parameterized (which is what the rest of this method deals with)
            // so it can only be created with a single configuration.
            return RxErrorHandlingCallAdapter<Any?>(retrofit,
                    Void::class.java,
                    scheduler,
                    isAsync,
                    isResult = false,
                    isBody = true,
                    isFlowable = false,
                    isSingle = false,
                    isMaybe = false,
                    isCompletable = true)
        }
        val isFlowable = rawType == Flowable::class.java
        val isSingle = rawType == Single::class.java
        val isMaybe = rawType == Maybe::class.java
        if (rawType != Observable::class.java && !isFlowable && !isSingle && !isMaybe) {
            return null
        }
        var isResult = false
        var isBody = false
        val responseType: Type
        if (returnType !is ParameterizedType) {
            val name = if (isFlowable) "Flowable" else if (isSingle) "Single" else if (isMaybe) "Maybe" else "Observable"
            throw IllegalStateException(name
                    + " return type must be parameterized"
                    + " as "
                    + name
                    + "<Foo> or "
                    + name
                    + "<? extends Foo>")
        }
        val observableType = getParameterUpperBound(0, returnType)
        when (getRawType(observableType)) {
            Response::class.java -> {
                check(observableType is ParameterizedType) {
                    ("Response must be parameterized" + " as Response<Foo> or Response<? extends Foo>")
                }
                responseType = getParameterUpperBound(0, observableType)
            }
            Result::class.java -> {
                check(observableType is ParameterizedType) {
                    ("Result must be parameterized" + " as Result<Foo> or Result<? extends Foo>")
                }
                responseType = getParameterUpperBound(0, observableType)
                isResult = true
            }
            else -> {
                responseType = observableType
                isBody = true
            }
        }
        return RxErrorHandlingCallAdapter<Any?>(retrofit,
                responseType,
                scheduler,
                isAsync,
                isResult,
                isBody,
                isFlowable,
                isSingle,
                isMaybe,
                false)
    }
}