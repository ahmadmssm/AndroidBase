package ams.android_base.baseClasses.network;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ams.android_base.utils.gson.Date2JsonAdapter;
import ams.android_base.utils.gson.Json2DateAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseAPIsConnector {

    private static Retrofit retrofit;

    // OKhttp
    protected OkHttpClient.Builder getOkHttpBuilder() {
        int timeOut = 200;
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS);
    }
    // Retrofit
    protected Retrofit getRetrofit () { return retrofit; }
    protected Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                    .baseUrl(getBaseURL())
                    .addConverterFactory(getConverterFactory())
                    .client(getOkHttpBuilder().build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
    protected final void buildRetrofit () { if (retrofit == null) retrofit = getRetrofitBuilder().build(); }
    protected <APIsInterface> APIsInterface getRetrofitClient(Class<APIsInterface> restAPIsInterface) {
        buildRetrofit();
        return retrofit.create(restAPIsInterface);
    }
    // Gson converters
    protected Gson getCustomGsonConverter(String serverDateFormat, String targetDateFormat) {
        return getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter(targetDateFormat))
                .registerTypeAdapter(Date.class, new Date2JsonAdapter(serverDateFormat))
                .create();
    }
    protected Gson getCustomGsonConverter(String serverDateFormat) {
        return getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter())
                .registerTypeAdapter(Date.class, new Date2JsonAdapter(serverDateFormat))
                .create();
    }
    protected Gson getCustomGsonConverter() {
        return getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter())
                .registerTypeAdapter(Date.class, new Date2JsonAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
    // Gson builder
    @SuppressWarnings("WeakerAccess")
    protected GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .setVersion(1.0);
    }
    //
    protected abstract String getBaseURL ();
    protected abstract Converter.Factory getConverterFactory ();

}
