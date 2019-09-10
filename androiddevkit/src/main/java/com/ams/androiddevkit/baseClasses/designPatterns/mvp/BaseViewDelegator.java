package com.ams.androiddevkit.baseClasses.designPatterns.mvp;

public interface BaseViewDelegator {
    default void showLoading() {}
    default void hideLoading() {}
    default void onGoOnline() {}
    default void onGoOffline() {}
    default void onLogOut() {}
}
