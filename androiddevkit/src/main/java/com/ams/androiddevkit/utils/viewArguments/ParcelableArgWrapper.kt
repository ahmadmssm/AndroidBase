package com.ams.androiddevkit.utils.viewArguments

import android.os.Parcelable

open class ParcelableArgWrapper<T: Parcelable>: BaseViewArgs() {

    var obj: T? = null
        private set

    companion object {
        fun <T: Parcelable> bundleOf(obj: T) = ParcelableArgWrapper<T>().apply { this.obj = obj }.build()
    }
}

