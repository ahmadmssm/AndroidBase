package com.ams.androidkitapplication.scenes.activites.ZoomImageActivityScene

import com.ams.androiddevkit.BaseZoomImageActivity
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseView

class ZoomImage: BaseZoomImageActivity<BasePresenter<BaseView>>() {

    override fun initPresenter(): BasePresenter<BaseView> {
        return BasePresenter(this)
    }

    override fun renderImageView() {}
}