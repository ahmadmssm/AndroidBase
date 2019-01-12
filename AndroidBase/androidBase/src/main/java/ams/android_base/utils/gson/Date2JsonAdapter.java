package ams.android_base.utils.gson;

import android.annotation.SuppressLint;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static android.provider.Settings.System.DATE_FORMAT;

public class Date2JsonAdapter implements JsonSerializer<Date> {

    private String format;

    @SuppressWarnings("unused")
    public Date2JsonAdapter() {}

    @SuppressWarnings("WeakerAccess")
    public Date2JsonAdapter(String format) { this.format = format; }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        // Set the date format to the default mobile date format
        if (format == null) format = DATE_FORMAT;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getDefault());
        String dateFormatAsString = formatter.format(date);
        return new JsonPrimitive(dateFormatAsString);
    }

}