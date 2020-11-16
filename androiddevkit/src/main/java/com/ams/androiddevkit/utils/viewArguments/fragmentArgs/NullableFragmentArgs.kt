package com.ams.androiddevkit.utils.viewArguments.fragmentArgs

import androidx.fragment.app.Fragment
import com.ams.androiddevkit.utils.viewArguments.BaseViewArgs
import com.ams.androiddevkit.utils.viewArguments.BaseViewArgsDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class NullableFragmentArgs<A: BaseViewArgs>(clazz: KClass<A>): BaseViewArgsDelegate<A>(clazz), ReadOnlyProperty<Fragment, A?> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): A? {
        thisRef.arguments?.let { bundle ->
            return this.getBundleArgsClass(bundle)
        }
        return null
    }
}