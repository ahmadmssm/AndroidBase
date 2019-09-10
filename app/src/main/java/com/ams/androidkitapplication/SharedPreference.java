package com.ams.androidkitapplication;


import com.google.gson.annotations.Expose;

import net.yslibrary.simplepreferences.annotation.Key;
import net.yslibrary.simplepreferences.annotation.Preferences;

// Default name is determined by class name
//@PreferenceEntity

@Preferences
public class SharedPreference {
//
//    @KeyName(name = "user")
//    @TypeConverter(converter = SharedPreferenceTypeConverter.class)
//    protected User user;

    @interface keys {
        String userId = "userId";
    }

    @Key()
    protected int userId = 0;

    @Key
    protected String userName = "";

    @Key(name = keys.userId, omitGetterPrefix = true)
    protected boolean isPremium = false;

}
