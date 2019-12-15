@file:Suppress("unused")

package com.ams.androiddevkit.baseClasses.networking.retrofitErrorHandler

import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

/**
 * HTTP response body converted to specified `type`. `null` if there is no
 * response.
 * @throws IOException if unable to convert the body to the specified `type`.
 */
@Throws(IOException::class)
fun <T>Throwable.getErrorBodyAs(type: Class<T>): T? {
    if (this is RetrofitException) {
        val exception: RetrofitException = this
        return exception.getErrorBodyAs(type)
    }
    return null
}

fun Throwable.asRetrofitException(retrofit: Retrofit): RetrofitException {
    // We had non 200-399 range http status code
    if (this is HttpException) {
        val response = this.response()
        return RetrofitException.httpError(response?.raw()?.request?.url.toString(), response!!, retrofit)
    }
    // A network error happened
    if (this is IOException) {
        return RetrofitException.networkError(this)
    }
    // We don't know what happened. We need to simply convert to an unknown error
    return RetrofitException.unexpectedError(this)
}