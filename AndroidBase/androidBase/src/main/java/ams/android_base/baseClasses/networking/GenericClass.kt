package ams.android_base.baseClasses.networking

import com.google.common.reflect.TypeToken
import com.google.gson.internal.`$Gson$Types`

class GenericClass<T>(private val rawType: Class<*>) {

    constructor():this(`$Gson$Types`.getRawType(object : TypeToken<T>() {}.getType()))

    fun getRawType(): Class<T> {
        return rawType as Class<T>
    }
}
