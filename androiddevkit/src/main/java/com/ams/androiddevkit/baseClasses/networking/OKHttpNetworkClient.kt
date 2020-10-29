package com.ams.androiddevkit.baseClasses.networking

import com.ams.androiddevkit.baseClasses.networking.interceptors.BaseRefreshTokenInterceptor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.*
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

abstract class OKHttpNetworkClient {

    open val okHttpClient: OkHttpClient by lazy { getOkHttpBuilder().build() }

    protected open fun getOkHttpBuilder(): OkHttpClient.Builder {
        val timeOut = getDefaultTimeOut()
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .addInterceptor(object: Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = addRequestExtras(chain.request())
                    return chain.proceed(request)
                }
            })
        if (getRefreshTokenInterceptor() != null)
            okHttpBuilder.addInterceptor(getRefreshTokenInterceptor()!!)
        if (getAdditionalInterceptors().isNotEmpty()) {
            for (interceptor in getAdditionalInterceptors()) {
                okHttpBuilder.addInterceptor(interceptor)
            }
        }
        if (getAdditionalNetworkInterceptors().isNotEmpty()) {
            for (interceptor in getAdditionalNetworkInterceptors()) {
                okHttpBuilder.addNetworkInterceptor(interceptor)
            }
        }
        okHttpBuilder.cache(getOKHttpCache())
        if (enablePrettyPrintLogging())
            okHttpBuilder.addInterceptor(getHttpPrettyPrintLoggingInterceptor())
        else if (isDebuggable())
            okHttpBuilder.addInterceptor(getOkHttpLoggingInterceptor())
        return okHttpBuilder
    }

    protected open fun addRequestExtras(originalRequest: Request): Request {
        var request: Request = originalRequest
        // Add additional Headers
        if (!getAdditionalHeaders().isNullOrEmpty()) {
            val requestBuilder = request.newBuilder()
            for ((header, value) in getAdditionalHeaders()) {
                requestBuilder.addHeader(header, value)
            }
            request = requestBuilder.build()
        }
        // Add additional Query params
        if (!getAdditionalQueryParams().isNullOrEmpty()) {
            val urlBuilder: HttpUrl.Builder = request.url.newBuilder()
            for ((parameter, value) in getAdditionalQueryParams()) {
                urlBuilder.addQueryParameter(parameter, value)
            }
            request = request.newBuilder().url(urlBuilder.build()).build()
        }
        // Add additional encoded Query params
        if (!getAdditionalEncodedQueryParams().isNullOrEmpty()) {
            val urlBuilder: HttpUrl.Builder = request.url.newBuilder()
            for ((parameter, value) in getAdditionalEncodedQueryParams()) {
                urlBuilder.addEncodedQueryParameter(parameter, value)
            }
            request = request.newBuilder().url(urlBuilder.build()).build()
        }
        return request
    }

    protected open fun getOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        // Set desired logging level -> Default is Full
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return  loggingInterceptor
    }

    // https://github.com/ihsanbal/LoggingInterceptor
    protected open fun getHttpPrettyPrintLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .loggable(isDebuggable())
            .log(Platform.INFO)
            // enable fix for logcat logging issues with pretty format
            // .enableAndroidStudio_v3_LogsHack(true)
            .build()
    }

    // Seconds.
    protected open fun getDefaultTimeOut(): Int = 10

    protected abstract fun isDebuggable(): Boolean

    protected open fun enablePrettyPrintLogging(): Boolean { return false }

    protected open fun getRefreshTokenInterceptor(): BaseRefreshTokenInterceptor<*>? = null

    // If we need to add Header(s) to every request
    protected open fun getAdditionalHeaders(): MutableMap<String, String> { return mutableMapOf() }

    // If we need to add Query param(s) to every request
    protected open fun getAdditionalQueryParams(): MutableMap<String, String> { return mutableMapOf() }

    // If we need to add encoded Query param(s) to every request
    protected open fun getAdditionalEncodedQueryParams(): MutableMap<String, String> { return mutableMapOf() }

    protected open fun getAdditionalInterceptors(): MutableList<Interceptor> { return mutableListOf() }

    protected open fun getAdditionalNetworkInterceptors(): MutableList<Interceptor> { return mutableListOf() }

    protected open fun getOKHttpCache(): Cache? = null
}
