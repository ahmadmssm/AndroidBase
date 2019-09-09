package ams.android_base.baseClasses.networking;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {


    public BaseRestClient() {}

    public APIs getAPIs() {
        Class<APIs> apIsClassType = (Class<APIs>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return getRetrofitClient(apIsClassType);
    }

//    public APIs getAPIs() {
//        Class<APIs> clazz = new GenericClass<APIs>().getRawType();
//        return retrofit.create(clazz);
//    }
}