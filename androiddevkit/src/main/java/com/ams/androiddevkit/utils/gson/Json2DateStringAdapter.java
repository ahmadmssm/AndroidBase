package com.ams.androiddevkit.utils.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Json2DateStringAdapter implements JsonDeserializer<String> {

    private final String TAG = Json2DateAdapter.class.getSimpleName();
    private String targetFormat;
    private final String[] dateFormats = new String[] {
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "EEE MMM dd HH:mm:ss z yyyy",
            "HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss aaa",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'",
            "EEE MMM DD HH:mm:ss z:00 yyyy",
            "MMM d',' yyyy H:mm:ss a"
    };

    @SuppressWarnings("unused")
    public Json2DateStringAdapter() {}

    public Json2DateStringAdapter(String targetFormat) { this.targetFormat = targetFormat; }

    @Override
    public String deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
            throws JsonParseException {
        for (String dateFormat : dateFormats) {
            try {
                DateFormat serverDateFormatter  = new SimpleDateFormat(dateFormat, Locale.getDefault());
                Date date =  serverDateFormatter.parse(jsonElement.getAsString());
                if (targetFormat == null) return Objects.requireNonNull(date).toString();
                else {
                    DateFormat dateFormatter = new SimpleDateFormat(targetFormat, Locale.getDefault());
                    return dateFormatter.format(date);
                }
            }
            catch (ParseException ignored) {}
        }
        return jsonElement.getAsString();
    }
}
