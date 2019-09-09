package ams.android_base.baseClasses.networking;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRestClient<APIs> extends BaseRetrofitClient {

    private Class<APIs> apIsClassType;


    public BaseRestClient() {
        this.apIsClassType = (Class<APIs>) getGenericClassType(0);
    }

    public APIs getAPIs() {
        return retrofit.create(apIsClassType);
    }

//    public APIs getAPIs() {
//        Class<APIs> clazz = new GenericClass<APIs>().getRawType();
//        return retrofit.create(clazz);
//    }

    /**
     * Returns a {@link Type} object to identify generic types
     * @return type
     */
    private Type getGenericClassType(int index) {

        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType)) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        return ((ParameterizedType) type).getActualTypeArguments()[index];
    }
}