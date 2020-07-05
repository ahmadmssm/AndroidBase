package com.ams.androiddevkit.baseClasses.networking.retrofitErrorHandler

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class RetrofitException(override val message: String?,
                             @Suppress("unused") val url: String?,
                             val response: Response<*>?,
                             val type: TYPE,
                             @Suppress("CanBeParameter") val exception: Throwable?,
                             val retrofit: Retrofit?): RuntimeException(message, exception) {

    fun getStatusCode(): Int? = this.response?.code()

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, TYPE.HTTP, null, retrofit)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, TYPE.NETWORK, exception, null)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message, null, null, TYPE.UNEXPECTED, exception, null)
        }
    }

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     * @throws IOException if unable to convert the body to the specified `type`.
     */
    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response?.errorBody() == null || retrofit == null) return null
        val converter: Converter<ResponseBody, T> = retrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
        return converter.convert(response.errorBody()!!)
    }

    enum class TYPE {
        /** An [IOException] occurred while communicating with the server.  */
        NETWORK,
        /** A non 200-399 HTTP status code range was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}