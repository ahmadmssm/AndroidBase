package ams.android_base.baseClasses.networking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseRetrofitClient {

    protected Retrofit retrofit;


    public BaseRetrofitClient() {}

    protected Builder getOkHttpBuilder() {
        int timeOut = getDefaultTimeOut();
        return new Builder()
                .connectTimeout((long)timeOut, TimeUnit.SECONDS)
                .writeTimeout((long)timeOut, TimeUnit.SECONDS)
                .readTimeout((long)timeOut, TimeUnit.SECONDS);
    }

    protected int getDefaultTimeOut() { return 200; };

    protected retrofit2.Retrofit.Builder getRetrofitBuilder() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(this.getBaseURL())
                .addConverterFactory(this.getConverterFactory())
                .client(this.getOkHttpBuilder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public final BaseRetrofitClient buildRetrofit() {
        if (retrofit == null) retrofit = this.getRetrofitBuilder().build();
        return this;
    }

    public <APIsInterface> APIsInterface getRetrofitClient(Class<APIsInterface> restAPIsInterface) {
        return retrofit.create(restAPIsInterface);
    }

    protected abstract String getBaseURL();

    protected abstract Factory getConverterFactory();

}
