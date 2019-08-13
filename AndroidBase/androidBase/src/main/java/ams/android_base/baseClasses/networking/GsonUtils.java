package ams.android_base.baseClasses.networking;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import ams.android_base.utils.gson.Date2JsonAdapter;
import ams.android_base.utils.gson.Json2DateAdapter;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GsonUtils {
    public Gson getCustomGsonConverter(String serverDateFormat, String targetDateFormat) {
        return this
                .getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter(targetDateFormat))
                .registerTypeAdapter(Date.class, new Date2JsonAdapter(serverDateFormat))
                .create();
    }

    public Gson getCustomGsonConverter(String serverDateFormat) {
        return this
                .getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter())
                .registerTypeAdapter(Date.class, new Date2JsonAdapter(serverDateFormat))
                .create();
    }

    public Gson getCustomGsonConverter() {
        return this
                .getGsonBuilder()
                .registerTypeAdapter(Date.class, new Json2DateAdapter())
                .registerTypeAdapter(Date.class, new Date2JsonAdapter())
                .create();
    }


    protected FieldNamingPolicy getFieldNamingPolicy() {
        return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    }

    public GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(getFieldNamingPolicy())
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .setVersion(1.0D);
    }
}
