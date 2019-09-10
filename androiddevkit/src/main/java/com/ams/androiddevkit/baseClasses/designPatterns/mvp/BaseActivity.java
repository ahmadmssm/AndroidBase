package com.ams.androiddevkit.baseClasses.designPatterns.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.ams.androiddevkit.utils.RuntimePermissionsManger;

import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressWarnings("unused")
public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity implements BaseViewDelegator {

    private Presenter presenter;
    private Unbinder butterKnifeUnBinder;
    private RuntimePermissionsManger runtimePermissionsManger;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        bindActivityViews();
        presenter = initPresenter();
        runtimePermissionsManger = new RuntimePermissionsManger(this);
    }

    protected void bindActivityViews () { butterKnifeUnBinder = ButterKnife.bind(this); }

    protected final RuntimePermissionsManger getRuntimePermissionsManger() { return runtimePermissionsManger; }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }

    protected final void unBind() { if (butterKnifeUnBinder != null) butterKnifeUnBinder.unbind(); }

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
