package ams.androidbase.androidbase.managers.networking

import ams.android_base.baseClasses.networking.BaseRetrofitClient
import ams.android_base.baseClasses.networking.GsonUtils
import ams.androidbase.androidbase.BuildConfig
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class APIsConnector: BaseRetrofitClient() {

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonUtils().customGsonConverter)
    }

    override fun getBaseURL(): String {
        return BuildConfig.APPLICATION_ID
    }


    // Static functions
    companion object {
        @JvmStatic
        fun getRestClient (): RestAPIs { return APIsConnector().getRetrofitClient(RestAPIs::class.java) }
    }
}