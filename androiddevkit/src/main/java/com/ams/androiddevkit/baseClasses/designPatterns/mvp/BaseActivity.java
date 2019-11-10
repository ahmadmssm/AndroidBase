package com.ams.androiddevkit.baseClasses.designPatterns.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.ams.androiddevkit.utils.RuntimePermissionsManger;

@SuppressWarnings("unused")
public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity implements BaseViewDelegator {

    private Presenter presenter;
    private RuntimePermissionsManger runtimePermissionsManger;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        presenter = initPresenter();
        runtimePermissionsManger = new RuntimePermissionsManger(this);
    }

    protected final RuntimePermissionsManger getRuntimePermissionsManger() { return runtimePermissionsManger; }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract
    @LayoutRes
    int getLayout();

    protected abstract Presenter initPresenter();

    public final Presenter getPresenter() { return presenter; }

    @Override
    public void showLoading() { }

    @Override
    public void hideLoading() { }

}
