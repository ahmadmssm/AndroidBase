package com.ams.androiddevkit.baseClasses.networking;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import kotlin.reflect.KClass;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {

    public APIs getAPIs() {
        @SuppressWarnings("unchecked")
        Class<APIs> apIsClassType = (Class<APIs>) ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        return buildRetrofit().getRetrofitClient(apIsClassType);
    }
}