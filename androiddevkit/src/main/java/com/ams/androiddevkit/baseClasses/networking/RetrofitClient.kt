package com.ams.androiddevkit.baseClasses.networking

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.Converter.Factory

abstract class RetrofitClient: OKHttpNetworkClient(), IRetrofitClient {

    protected open var retrofit: Retrofit? = null

    protected open fun getRetrofitBuilder(): Retrofit.Builder {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(this.getBaseURL())
            .client(okHttpClient)
        if (getConverterFactory() != null) retrofitBuilder.addConverterFactory(this.getConverterFactory()!!)
        val callAdapterFactory =
            if (getRxErrorHandlingCallAdapterFactory() != null) getRxErrorHandlingCallAdapterFactory()!!
            else RxJava3CallAdapterFactory.create()
        retrofitBuilder.addCallAdapterFactory(callAdapterFactory)
        return retrofitBuilder
    }

    fun buildRetrofit(): RetrofitClient {
        if (retrofit == null)
            retrofit = this.getRetrofitBuilder().build()
        return this
    }

    override fun <APIsInterface> getRetrofitClient(restAPIsInterface: Class<APIsInterface>): APIsInterface {
        return retrofit!!.create(restAPIsInterface)
    }

    protected open fun getConverterFactory(): Factory? = null

    protected abstract fun getBaseURL(): String

    // If we need to add RxErrorHandlingCallAdapterFactory for error handling
    protected open fun getRxErrorHandlingCallAdapterFactory(): CallAdapter.Factory? { return null }
}