package ams.android_base.baseClasses.networking;

import java.lang.reflect.ParameterizedType;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {

    public BaseRestClient() {
        this.buildRetrofit();
    }

    public APIs getAPIs() {
        Class<APIs> apIsClassType = (Class<APIs>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return getRetrofitClient(apIsClassType);
    }
}