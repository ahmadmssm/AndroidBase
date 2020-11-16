package com.ams.androiddevkit.utils.viewArguments.activityArgs

import android.app.Activity
import com.ams.androiddevkit.utils.viewArguments.BaseViewArgs
import com.ams.androiddevkit.utils.viewArguments.BaseViewArgsDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("unused")
class NullableActivityArgs<A: BaseViewArgs>(clazz: KClass<A>): BaseViewArgsDelegate<A>(clazz), ReadOnlyProperty<Activity, A?> {

    override fun getValue(thisRef: Activity, property: KProperty<*>): A? {
        thisRef.intent.extras?.let { bundle ->
            return this.getBundleArgsClass(bundle)
        }
        return null
    }
}