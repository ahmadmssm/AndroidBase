package com.ams.androidkitapplication.managers.networking

import com.ams.androiddevkit.baseClasses.networking.BaseRestClient
import com.ams.androiddevkit.utils.services.gsonService.GsonService
import com.ams.androiddevkit.utils.services.gsonService.GsonServiceImpl
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class AppRestClient: BaseRestClient() {

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonServiceImpl().getGsonConverter())
    }

    override fun getBaseURL() = "https://restcountries.eu/rest/v2/"

    // Static functions
    companion object {
        @JvmStatic
        fun getRestClient (): RestAPIs { return AppRestClient().getRetrofitClient(RestAPIs::class.java) }
    }
}