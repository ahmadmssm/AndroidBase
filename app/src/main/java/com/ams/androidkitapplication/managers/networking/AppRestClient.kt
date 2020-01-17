package com.ams.androidkitapplication.managers.networking

import com.ams.androiddevkit.baseClasses.networking.BaseRestClient
import com.ams.androiddevkit.baseClasses.networking.GsonUtils
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class AppRestClient: BaseRestClient() {

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonUtils().getCustomGsonConverter())
    }

    override fun getBaseURL(): String {
        return "https://restcountries.eu/rest/v2/"
        // return BuildConfig.APPLICATION_ID
    }

    // Static functions
    companion object {
        @JvmStatic
        fun getRestClient (): RestAPIs { return AppRestClient().getRetrofitClient(RestAPIs::class.java) }
    }
}