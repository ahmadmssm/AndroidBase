package ams.androidbase.androidbase.scenes.ZoomImageScene

import ams.android_base.ZoomImageActivity
import ams.androidbase.androidbase.R


class ZoomImage: ZoomImageActivity() {

    override fun renderImageView() {
       imageView.setImageResource(R.mipmap.ic_launcher)
    }

}