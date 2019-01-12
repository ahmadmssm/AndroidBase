package ams.androidbase.androidbase.managers

import ams.android_base.baseClasses.network.BaseAPIsConnector
import ams.androidbase.androidbase.BuildConfig
import ams.androidbase.androidbase.bb
import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

class APIsConnector: BaseAPIsConnector() {

    override fun getBaseURL(): String { return BuildConfig.APPLICATION_ID }

    override fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(getCustomGsonConverter("EEE MMM DD HH:mm:ss z:00 yyyy"))
    }

    // Static functions
    companion object {
        @JvmStatic
        fun getConnector (): RestAPIs { return APIsConnector().getRetrofitClient(RestAPIs::class.java) }
    }

}