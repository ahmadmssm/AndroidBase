package ams.androidbase.androidbase.scenes.ZoomImageScene

import ams.android_base.BaseZoomImageActivity
import ams.android_base.R.id.imageView
import ams.android_base.baseClasses.mvp.BasePresenter
import ams.android_base.baseClasses.mvp.BaseViewDelegator
import ams.androidbase.androidbase.R


class ZoomImage: BaseZoomImageActivity<BasePresenter>() {

    override fun initPresenter(): { return BasePresenter<BaseViewDelegator>(this) }

    override fun renderImageView() { imageView.setImageResource(R.mipmap.ic_launcher) }

}