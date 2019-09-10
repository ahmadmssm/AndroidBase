package ams.androidbase.androidbase.scenes.activites.ZoomImageActivityScene

import ams.android_base.BaseZoomImageActivity
import ams.android_base.baseClasses.designPatterns.mvp.BasePresenter
import ams.android_base.baseClasses.designPatterns.mvp.BaseViewDelegator


class ZoomImage: BaseZoomImageActivity<BasePresenter<BaseViewDelegator>>() {

    override fun initPresenter(): BasePresenter<BaseViewDelegator> {
        return BasePresenter(this)
    }

    override fun renderImageView() {}

}