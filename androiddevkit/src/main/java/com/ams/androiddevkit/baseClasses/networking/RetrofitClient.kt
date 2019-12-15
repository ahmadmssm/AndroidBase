package com.ams.androiddevkit.baseClasses.networking

import com.ams.androiddevkit.baseClasses.networking.mock.RequestFilter
import com.ams.androiddevkit.baseClasses.networking.mock.addResponseMocks
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient.Builder
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.Converter.Factory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException

abstract class RetrofitClient: IRetrofitClient {

    protected open var retrofit: Retrofit? = null

    protected open fun getOkHttpBuilder(): Builder {
        val timeOut = getDefaultTimeOut()
        val okHttpBuilder = Builder()
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
        if (getRefreshTokenInterceptor() != null) okHttpBuilder.addInterceptor(getRefreshTokenInterceptor()!!)
        if (isMockable() && getResponseMocks().isNotEmpty()) okHttpBuilder.addResponseMocks(getResponseMocks())
        if (getAdditionalInterceptors().isNotEmpty()) {
            for (interceptor in getAdditionalInterceptors()) {
                okHttpBuilder.addInterceptor(interceptor)
            }
        }
        if (enablePrettyPrintLogging()) okHttpBuilder.addInterceptor(getHttpPrettyPrintLoggingInterceptor())
        else if (isDebuggable()) okHttpBuilder.addInterceptor(getOkHttpLoggingInterceptor())
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
        // set desired log level -> Default is Full
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

    protected open fun getRetrofitBuilder(): Retrofit.Builder {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(this.getBaseURL())
            .addConverterFactory(this.getConverterFactory())
            .client(this.getOkHttpBuilder().build())
        if (getRxErrorHandlingCallAdapterFactory() != null)
            retrofitBuilder.addCallAdapterFactory(getRxErrorHandlingCallAdapterFactory()!!)
        else
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return retrofitBuilder
    }

    fun buildRetrofit(): RetrofitClient {
        if (retrofit == null) retrofit = this.getRetrofitBuilder().build()
        return this
    }

    override fun <APIsInterface> getRetrofitClient(restAPIsInterface: Class<APIsInterface>): APIsInterface {
        return retrofit!!.create(restAPIsInterface)
    }

    protected abstract fun getDefaultTimeOut(): Int

    protected abstract fun isMockable(): Boolean

    protected abstract fun isDebuggable(): Boolean

    protected open fun enablePrettyPrintLogging(): Boolean { return false }

    protected abstract fun getConverterFactory(): Factory

    protected abstract fun getBaseURL(): String

    protected abstract fun getRefreshTokenInterceptor(): TokenInterceptor?

    // If we need to add Header(s) to every request
    protected open fun getAdditionalHeaders(): MutableMap<String, String> { return mutableMapOf() }

    // If we need to add Query param(s) to every request
    protected open fun getAdditionalQueryParams(): MutableMap<String, String> { return mutableMapOf() }

    // If we need to add RxErrorHandlingCallAdapterFactory for error handling
    protected open fun getRxErrorHandlingCallAdapterFactory(): CallAdapter.Factory? { return null }

    // If we need to add encoded Query param(s) to every request
    protected open fun getAdditionalEncodedQueryParams(): MutableMap<String, String> { return mutableMapOf() }

    protected open fun getAdditionalInterceptors(): MutableList<Interceptor> { return mutableListOf() }

    protected open fun getResponseMocks(): MutableMap<RequestFilter, MockResponse> { return mutableMapOf() }
}
