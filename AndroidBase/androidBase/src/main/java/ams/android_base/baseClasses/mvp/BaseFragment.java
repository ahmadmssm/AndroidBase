package ams.android_base.baseClasses.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<Presenter extends BasePresenter> extends Fragment implements BaseViewDelegator {

    private Presenter presenter;
    private Unbinder butterKnifeUnBinder;

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
    }


    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        unBind();
    }

    protected void bindFragmentViews (Fragment fragment, View view) {
        butterKnifeUnBinder = ButterKnife.bind(fragment, view);
    }

    protected final void unBind() {
        if (butterKnifeUnBinder != null)
            butterKnifeUnBinder.unbind();
    }

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
