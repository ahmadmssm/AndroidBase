package com.ams.androidkitapplication.scenes.activites.ZoomImageActivityScene

import com.ams.androiddevkit.BaseZoomImageActivity
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BasePresenter
import com.ams.androiddevkit.baseClasses.designPatterns.mvp.BaseViewDelegator


class ZoomImage: BaseZoomImageActivity<BasePresenter<BaseViewDelegator>>() {

    override fun initPresenter(): BasePresenter<BaseViewDelegator> {
        return BasePresenter(this)
    }

    override fun renderImageView() {}
}