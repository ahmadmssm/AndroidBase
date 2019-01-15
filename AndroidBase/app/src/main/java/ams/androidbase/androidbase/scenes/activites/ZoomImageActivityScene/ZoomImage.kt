package ams.androidbase.androidbase.scenes.activites.ZoomImageActivityScene

import ams.android_base.BaseZoomImageActivity
import ams.android_base.baseClasses.mvp.BasePresenter
import ams.android_base.baseClasses.mvp.BaseViewDelegator
import ams.androidbase.androidbase.R


class ZoomImage: BaseZoomImageActivity<BasePresenter<BaseViewDelegator>>() {

    override fun initPresenter(): BasePresenter<BaseViewDelegator> { return BasePresenter(this) }

    override fun renderImageView() { imageView.setImageResource(R.mipmap.ic_launcher) }

}