package com.ams.androiddevkit.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ahmad Mahmoud on 12-Feb-18.
 */

@SuppressWarnings("unused")
public class GenericTypeConverter {

    private GenericTypeConverter() {}

    private final static Gson gson = new Gson();

    public static <T> List<T> listOfObjectsFromString (String string, Class<T[]> clazz) {
        T[] list = gson.fromJson(string, clazz);
        return Arrays.asList(list);
    }
    public static <T> T objectFromString (String string, Class<T> t) { return gson.fromJson(string, t); }
    public static <T> String objectToString (Class<T> t) { return gson.toJson(t); }
    public static <T> ArrayList<T> listFromString(String string) {
        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return new Gson().fromJson(string, listType);
    }
    public static <T> String fromArrayList(ArrayList<T> list) { return gson.toJson(list); }

}
