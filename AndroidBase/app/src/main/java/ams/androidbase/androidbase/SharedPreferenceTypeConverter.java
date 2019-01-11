package ams.androidbase.androidbase;

import com.skydoves.preferenceroom.PreferenceTypeConverter;

import ams.android_base.utils.TypeConverter;

public class SharedPreferenceTypeConverter<T> extends PreferenceTypeConverter<T> {

    public SharedPreferenceTypeConverter(Class<T> clazz) { super(clazz); }

    @Override
    public String convertObject(T object) {
        return TypeConverter.objectToString(object.getClass());
    }

    @Override
    public T convertType(String string) {
        return TypeConverter.objectFromString(string, clazz);
    }

}
