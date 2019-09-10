package com.ams.androiddevkit;

import android.os.Bundle;
import android.view.WindowManager;

import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseActivity;
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public abstract class BaseZoomImageActivity<Presenter extends BasePresenter> extends BaseActivity<Presenter> {

    protected ImageViewTouch imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        renderImageView();
    }

    private void initView() {
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        // Remove activity title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected int getLayout() { return R.layout.activity_zoom_image; }

    protected abstract void renderImageView ();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
