package ams.android_base.helpers.checkers;

import android.os.Build;

/**
 * Created by Ahmad Mahmoud on 05-Feb-18.
 */

public class AndroidVersion {

    public static boolean isLollipopOrHigher () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNougatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isJellyBeanOrHigher () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
