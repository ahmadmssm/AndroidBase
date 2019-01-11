package ams.android_base.baseClasses.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseAPIsConnector {

    private static Retrofit retrofit;

    protected OkHttpClient.Builder getOkHttpBuilder() {
        int timeOut = 200;
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS);
    }

    protected Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                    .baseUrl(getBaseURL())
                    .addConverterFactory(getConverterFactory())
                    .client(getOkHttpBuilder().build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    protected final void buildRetrofit () { if (retrofit == null) retrofit = getRetrofitBuilder().build(); }

    protected Retrofit getRetrofit () { return retrofit; }

    protected <APIsInterface> APIsInterface getRetrofitClient(Class<APIsInterface> restAPIsInterface) {
        buildRetrofit();
        return retrofit.create(restAPIsInterface);
    }

    protected abstract String getBaseURL ();
    protected abstract Converter.Factory getConverterFactory ();

}
