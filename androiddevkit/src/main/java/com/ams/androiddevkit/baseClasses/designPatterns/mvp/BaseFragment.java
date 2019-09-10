package com.ams.androiddevkit.baseClasses.designPatterns.mvp;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ams.androiddevkit.utils.RuntimePermissionsManger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseFragment<Presenter extends BasePresenter> extends Fragment implements BaseViewDelegator {

    private Presenter presenter;
    private Unbinder butterKnifeUnBinder;
    private RuntimePermissionsManger runtimePermissionsManger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindFragmentViews(this, view);
        presenter = initPresenter();
        runtimePermissionsManger = new RuntimePermissionsManger(this);
    }


    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        unBind();
    }

    protected void bindFragmentViews (Fragment fragment, View view) { butterKnifeUnBinder = ButterKnife.bind(fragment, view); }

    protected final void unBind() { if (butterKnifeUnBinder != null) butterKnifeUnBinder.unbind(); }

    protected final RuntimePermissionsManger getRuntimePermissionsManger() { return runtimePermissionsManger; }

    protected abstract
    @LayoutRes
    int getLayout();

    protected abstract
    Presenter initPresenter();

    public final Presenter getPresenter() { return presenter; }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

}
