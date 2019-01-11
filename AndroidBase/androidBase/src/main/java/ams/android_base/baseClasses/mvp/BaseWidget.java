package ams.android_base.baseClasses.mvp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.widget.RemoteViews;

import java.util.Random;


public abstract class BaseWidget<Presenter extends BasePresenter> extends AppWidgetProvider implements BaseViewDelegator {

    private Presenter presenter;

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) { super.onDeleted(context, appWidgetIds); }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) { super.onRestored(context, oldWidgetIds, newWidgetIds); }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int widgetCount = appWidgetIds.length;
        /*
        In the onUpdate() method we iterate through all our widgets (In case the user has placed multiple widgets)
        to get RemoteViews object then update the RemoteView’s (widget_normal_layout's) textview with a new random number
        between 100 and 999 then specify the action that should occur when the Button is tapped.
        //
        To request a manual update when the update button is clicked, we use a PendingIntent.
        The action for the Intent is set to AppWidgetManager.ACTION_APPWIDGET_UPDATE.
        This is the same action sent by the system when the widget_normal_layout needs to be updated automatically.
        We also indicate the widgets that should be updated (all of the app widgets) by calling :
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds).
        To update the current widget_normal_layout only you can call :
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        Finally we request the AppWidgetManager object to update the app widget_normal_layout
        giving it the current widgetId and the current RemoteViews object.
        */
        for (int widgetLayoutId : appWidgetIds) {
            /*
            If your widget is displayed on a lock screen,
            you might want to show different data, or a different layout.
            To detect if the widget is on a lock screen you request the widget options
            using appWidgetManager.getAppWidgetOptions(int widgetId), This method returns a bundle
            which we can get options category (as int),
            This will either be a WIDGET_CATEGORY_HOME_SCREEN or WIDGET_CATEGORY_KEYGUARD (Lock screen).
            You can provide a different layout based on the result.
            */
            //
            Bundle widgetOptions = appWidgetManager.getAppWidgetOptions(widgetLayoutId);
            // Get the value of OPTION_APPWIDGET_HOST_CATEGORY
            int category = widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
            // If the value is WIDGET_CATEGORY_KEYGUARD, it's a lockscreen widget_normal_layout
            boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
            int baseLayout = isKeyguard ? getLockScreenLayout() : getLayout();
            //
            String number = String.format("%03d", (new Random().nextInt(900) + 100));
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.widget_normal_layout);
            updateWidget(context, appWidgetManager, widgetLayoutId, number);
        }
    }

    protected abstract void updateWidget(Context context, AppWidgetManager widgetLayoutId, int widgetId, String str);

    protected final RemoteViews getWidgetRemoteViews (Context context, int widgetLayoutId) {
        return new RemoteViews(context.getPackageName(), widgetLayoutId);
    }

    protected abstract Presenter initPresenter();

    public final Presenter getPresenter() { return presenter; }

    protected abstract
    @LayoutRes
    int getLayout();

    protected abstract
    @LayoutRes
    int getLockScreenLayout();

}
