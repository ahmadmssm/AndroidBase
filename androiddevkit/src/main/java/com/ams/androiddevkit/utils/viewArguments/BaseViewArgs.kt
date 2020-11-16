package com.ams.androiddevkit.utils.viewArguments

import android.os.Bundle
import com.ams.androiddevkit.utils.extensions.put
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

@Suppress("unused")
open class BaseViewArgs {
    fun build(): Bundle {
        val bundle = Bundle()
        this::class.memberProperties.forEach { field ->
            field.apply {
                if(!field.returnType.jvmErasure.isCompanion)
                    getter.call(this@BaseViewArgs)?.let {
                        // println(field.name)
                        // println(field.genericType)
                        // println(field.get(obj))
                        bundle.put(field.name, it)
                    }
            }
        }
        return bundle
    }
}