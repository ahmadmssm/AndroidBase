package com.ams.androiddevkit.baseClasses.networking

import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Converter.Factory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

abstract class RetrofitClient: IRetrofitClient {

    protected open var retrofit: Retrofit? = null

    protected open fun etOkHttpBuilder(): Builder {
        val timeOut = getDefaultTimeOut()
        val okHttpBuilder = Builder()
        okHttpBuilder
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        if (getRefreshTokenInterceptor() != null) {
            okHttpBuilder.addInterceptor(getRefreshTokenInterceptor()!!)
        }
        if (isDebuggable()) {
            okHttpBuilder.addInterceptor(getHttpLoggingInterceptor())
        }
        if (getAdditionalInterceptors().isNotEmpty()) {
            for (interceptor in getAdditionalInterceptors()) {
                okHttpBuilder.addInterceptor(interceptor)
            }
        }
        return okHttpBuilder
    }

    protected open fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        // set desired log level -> Default is Full
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return  loggingInterceptor
    }

    protected open fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(this.getBaseURL())
            .addConverterFactory(this.getConverterFactory())
            .client(this.etOkHttpBuilder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    protected abstract fun getConverterFactory(): Factory

    protected abstract fun getBaseURL(): String

    protected abstract fun getRefreshTokenInterceptor(): TokenInterceptor?

    protected open fun getAdditionalInterceptors(): MutableList<Interceptor> { return mutableListOf() }

}
