package ams.android_base.baseClasses.mvp;

import androidx.annotation.NonNull;

public class BasePresenter <ViewDelegator extends BaseViewDelegator> {

    private ViewDelegator viewDelegator;

    public BasePresenter (@NonNull ViewDelegator viewDelegator) { this.viewDelegator = viewDelegator; }

    protected ViewDelegator getViewDelegator() { return viewDelegator; }

}
