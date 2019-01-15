package ams.androidbase.androidbase.common;

public class Application extends android.app.Application {

    public static Application applicationInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
    }

}
