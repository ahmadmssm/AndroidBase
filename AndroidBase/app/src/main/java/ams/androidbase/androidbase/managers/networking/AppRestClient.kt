package ams.androidbase.androidbase.managers.networking

import ams.android_base.baseClasses.networking.BaseRestClient
import ams.android_base.baseClasses.networking.BaseRetrofitClient
import ams.android_base.baseClasses.networking.GsonUtils
import ams.androidbase.androidbase.BuildConfig
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class AppRestClient: BaseRestClient<RestAPIs>() {

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonUtils().customGsonConverter)
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