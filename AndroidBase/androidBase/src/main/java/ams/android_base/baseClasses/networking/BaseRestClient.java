package ams.android_base.baseClasses.networking;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {

    public BaseRestClient() {
        this.buildRetrofit();
    }

    public APIs getAPIs() {
        @SuppressWarnings("unchecked")
        Class<APIs> apIsClassType = (Class<APIs>) ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        return getRetrofitClient(apIsClassType);
    }
}