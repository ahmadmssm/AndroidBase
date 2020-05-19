package com.ams.androiddevkit

import android.os.Bundle
import android.view.WindowManager
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseActivity
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter
import it.sephiroth.android.library.imagezoom.ImageViewTouch

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseZoomImageActivity<Presenter: BasePresenter<*>> : BaseActivity<Presenter>() {

    protected var imageView: ImageViewTouch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        renderImageView()
    }

    private fun initView() {
        if (supportActionBar != null) supportActionBar!!.hide()
        // Remove activity title
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        imageView = findViewById(R.id.imageView)
    }

    override val layout = R.layout.activity_zoom_image

    protected abstract fun renderImageView()

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}