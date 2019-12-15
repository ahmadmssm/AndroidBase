package com.ams.androiddevkit.baseClasses.designPatterns.mvp;

import androidx.annotation.NonNull;

public class BasePresenter <ViewDelegator extends BaseView> {

    private ViewDelegator viewDelegator;

    public BasePresenter (@NonNull ViewDelegator viewDelegator) { this.viewDelegator = viewDelegator; }

    protected ViewDelegator getViewDelegator() { return viewDelegator; }

}
