package com.ams.androiddevkit.baseClasses.networking.error

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
        val statusCode = retrofitException.response?.code()
        return when (retrofitException.type) {
            RetrofitException.TYPE.HTTP -> {
                val errorModel = retrofitException.getErrorBodyAs(networkErrorModel)
                if (errorModel != null) {
                    onHttpError(retrofitException, statusCode, errorModel, iErrors)
                }
                else {
                    iErrors.onShowUnknownError(retrofitException)
                }
            }

            RetrofitException.TYPE.NETWORK -> {
                onNetworkError(retrofitException, iErrors)
            }

            RetrofitException.TYPE.UNEXPECTED -> {
                onUnexpectedNetworkError(retrofitException, iErrors)
            }
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