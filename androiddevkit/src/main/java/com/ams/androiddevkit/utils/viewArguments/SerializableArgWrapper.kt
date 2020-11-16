package com.ams.androiddevkit.utils.viewArguments

import java.io.Serializable

open class SerializableArgWrapper<T: Serializable>: BaseViewArgs() {

    var obj: T? = null
        private set

    companion object {
        fun <T: Serializable> bundleOf(obj: T) = SerializableArgWrapper<T>().apply { this.obj = obj }.build()
    }
}