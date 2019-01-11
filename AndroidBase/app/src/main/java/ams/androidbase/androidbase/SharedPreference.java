package ams.androidbase.androidbase;

import com.skydoves.preferenceroom.KeyName;
import com.skydoves.preferenceroom.PreferenceEntity;
import com.skydoves.preferenceroom.PreferenceFunction;
import com.skydoves.preferenceroom.TypeConverter;

// Default name is determined by class name
@PreferenceEntity
public class SharedPreference {

    // Share preference keys
    @interface Keys {
        String USER = "user";
    }

    @KeyName(name = Keys.USER)
    @TypeConverter(converter = SharedPreferenceTypeConverter.class)
    protected User user;



}
