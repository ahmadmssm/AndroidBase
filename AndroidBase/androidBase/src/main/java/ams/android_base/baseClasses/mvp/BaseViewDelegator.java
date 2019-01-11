package ams.android_base.baseClasses.mvp;

public interface BaseViewDelegator {
    default void showLoading() {}
    default void hideLoading() {}
    default void onGoOnline() {}
    default void onGoOffline() {}
    default void onLogOut() {}
}
