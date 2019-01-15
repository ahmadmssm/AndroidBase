package ams.android_base.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastHelper {

    private static Toast toast;

    public static void showToast (Context context, String text, int duration) {
        createToast(context, duration);
        toast.setText(text);
        toast.show();
    }

    public static void showToast (Context context, int textResourceId, int duration) {
        createToast(context, duration);
        toast.setText(textResourceId);
        toast.show();
    }

    private static void createToast(Context context, int duration) {
        if (toast != null) toast.cancel();
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
    }

}
