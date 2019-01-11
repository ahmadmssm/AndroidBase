package ams.android_base.baseClasses.mvp;

public interface BaseViewDelegator {
    public void showLoading();
    public void hideLoading();
    public void onGoOnline();
    public void onGoOffline();
    public void onLogOut();
}
