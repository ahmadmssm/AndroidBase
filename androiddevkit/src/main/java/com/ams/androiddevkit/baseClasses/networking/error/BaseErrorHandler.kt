package com.ams.androiddevkit.baseClasses.networking.error

import androidx.annotation.CallSuper
import com.ams.androiddevkit.baseClasses.networking.retrofitErrorHandler.RetrofitException

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseErrorHandler<IErrors: BaseIErrors, NetworkErrorModel>(protected val networkErrorModel: Class<NetworkErrorModel>) {

    open fun processError(exception: Throwable, iErrors: IErrors) {
        if (exception is RetrofitException)
            processRetrofitError(exception, iErrors)
        else
            onNonNetworkError(exception, iErrors)
    }

    protected open fun processRetrofitError(retrofitException: RetrofitException, iErrors: IErrors) {
        return when (retrofitException.type) {
            RetrofitException.TYPE.HTTP -> {
                onHttpError(retrofitException, iErrors)
            }

            RetrofitException.TYPE.NETWORK -> {
                onNetworkError(retrofitException, iErrors)
            }

            RetrofitException.TYPE.UNEXPECTED -> {
                onUnexpectedNetworkError(retrofitException, iErrors)
            }
        }
    }

    @CallSuper
    protected open fun onHttpError(retrofitException: RetrofitException, iErrors: IErrors) {
        val statusCode = retrofitException.getStatusCode()
        try {
            val errorModel = retrofitException.getErrorBodyAs(networkErrorModel)
            if (errorModel != null) {
                onHttpError(retrofitException, statusCode, errorModel, iErrors)
            }
            else {
                iErrors.onShowUnknownError(retrofitException)
            }
        }
        catch (e: Exception) {
            iErrors.onShowUnknownError(retrofitException)
        }
    }

    protected open fun onNonNetworkError(throwable: Throwable, iErrors: IErrors) {
        iErrors.onShowNonNetworkError(throwable)
    }

    protected open fun onNetworkError(exception: RetrofitException, iErrors: IErrors) {
        iErrors.onShowNetworkError(exception)
    }

    protected open fun onUnexpectedNetworkError(exception: RetrofitException, iErrors: IErrors) {
        iErrors.onShowUnknownError(exception)
    }

    protected abstract fun onHttpError(exception: RetrofitException,
                                       statusCode: Int?,
                                       errorModel: NetworkErrorModel,
                                       iErrors: IErrors)
}