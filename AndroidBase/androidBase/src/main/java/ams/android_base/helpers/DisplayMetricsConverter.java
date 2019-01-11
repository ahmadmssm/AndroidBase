package ams.android_base.helpers;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Ahmad Mahmoud on 22-Feb-18.
 */

public class DisplayMetricsConverter {

    private static DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

    public static float convertPixelsToDp(float px){
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp){
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

}
