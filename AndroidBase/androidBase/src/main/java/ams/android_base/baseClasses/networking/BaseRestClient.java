package ams.android_base.baseClasses.networking;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {

    public APIs getAPIs() {
        Class<APIs> clazz = new GenericClass<APIs>().getRawType();
        return retrofit.create(clazz);
    }
}