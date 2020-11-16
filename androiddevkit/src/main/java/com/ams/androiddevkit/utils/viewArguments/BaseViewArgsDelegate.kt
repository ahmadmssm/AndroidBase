package com.ams.androiddevkit.utils.viewArguments

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

@Suppress("SameParameterValue", "unused", "MemberVisibilityCanBePrivate")
open class BaseViewArgsDelegate<A: BaseViewArgs>(private val clazz: KClass<A>) {

    protected fun getBundleArgsClass(bundle: Bundle): A {
        clazz.qualifiedName?.let { className ->
            val anyClass = Class.forName(className).newInstance()
            @Suppress("UNCHECKED_CAST")
            (anyClass as? A)?.let { argsClass ->
                if(argsClass::class.isData)
                    throw Exception("Bundle arguments can not be a data class")
                else {
                    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
                    var isNullable: Boolean
                    argsClass::class.memberProperties.forEach { field ->
                        val key = field.name
                        // println(field.name)
                        isNullable = field.returnType.isMarkedNullable
                        field.isAccessible = true
                        if(field.returnType.jvmErasure.isSubclassOf(Boolean::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Boolean) ?: false
                                getBoolean(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Int::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Int) ?: 0
                                getInt(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(String::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null, then, let the default value to be the variable assigned value or null.
                                getString(bundle, key)?.let {
                                    field.setter.call(argsClass, it)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Short::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Short) ?: 0
                                getShort(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Long::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Long) ?: 0
                                getLong(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Byte::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Byte) ?: 0
                                getByte(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(ByteArray::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? ByteArray) ?: byteArrayOf()
                                getByteArray(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Char::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null, then, let the default value to be the variable assigned value or null.
                                getChar(bundle, key)?.let {
                                    field.setter.call(argsClass, it)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(CharArray::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? CharArray) ?: charArrayOf()
                                getCharArray(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(CharSequence::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null, then, let the default value to be the variable assigned value or null.
                                getCharSequence(bundle, key)?.let {
                                    field.setter.call(argsClass, it)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Float::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Float) ?: 0F
                                getFloat(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Double::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                val defaultValue = (field.getter.call(argsClass) as? Double) ?: 0.0
                                getDouble(bundle, key, defaultValue).let { bundleValue ->
                                    field.setter.call(argsClass, bundleValue)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Bundle::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null, then, let the default value to be the variable assigned value or null.
                                getBundle(bundle, key)?.let {
                                    field.setter.call(argsClass, it)
                                }
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Parcelable::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                field.setter.call(argsClass, getParcelable(bundle, key))
                            }
                        }
                        else if(field.returnType.jvmErasure.isSubclassOf(Serializable::class)) {
                            if (field is KMutableProperty<*>) {
                                // Try to get bundle value by variable name.
                                // If the value is null or the initial value of the data type.
                                // then, let the default value to be the variable assigned value or null.
                                field.setter.call(argsClass, getSerializable(bundle, key))
                            }
                        }
                        else
                            throw IllegalStateException("Type of property $key is not supported")
                        field.isAccessible = false
                    }
                }
                return argsClass
            }
        }
        throw Exception("Instance creation exception!")
    }

    protected fun getString(bundle: Bundle, key: String): String? {
        return bundle.getString(key)
    }

    protected fun getChar(bundle: Bundle, key: String): Char? {
        return bundle.getChar(key)
    }

    protected fun getCharSequence(bundle: Bundle, key: String): CharSequence? {
        return bundle.getCharSequence(key)
    }

    protected fun getBoolean(bundle: Bundle, key: String, defaultValue: Boolean): Boolean {
        return bundle.getBoolean(key, defaultValue)
    }

    protected fun getInt(bundle: Bundle, key: String, defaultValue: Int): Int {
        return bundle.getInt(key, defaultValue)
    }

    protected fun getShort(bundle: Bundle, key: String, defaultValue: Short): Short {
        return bundle.getShort(key, defaultValue)
    }

    protected fun getLong(bundle: Bundle, key: String, defaultValue: Long): Long {
        return bundle.getLong(key, defaultValue)
    }

    protected fun getByte(bundle: Bundle, key: String, defaultValue: Byte): Byte {
        return bundle.getByte(key, defaultValue)
    }

    protected fun getByteArray(bundle: Bundle, key: String, defaultValue: ByteArray): ByteArray {
        return bundle.getByteArray(key) ?: defaultValue
    }

    protected fun getCharArray(bundle: Bundle, key: String, defaultValue: CharArray): CharArray {
        return bundle.getCharArray(key) ?: defaultValue
    }

    protected fun getFloat(bundle: Bundle, key: String, defaultValue: Float): Float {
        return bundle.getFloat(key, defaultValue)
    }

    protected fun getDouble(bundle: Bundle, key: String, defaultValue: Double): Double {
        return bundle.getDouble(key, defaultValue)
    }

    protected fun getBundle(bundle: Bundle, key: String): Bundle? {
        return bundle.getBundle(key)
    }

    protected fun <T: Parcelable>getParcelable(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T: Serializable>getSerializable(bundle: Bundle, key: String): T? {
        return bundle.getSerializable(key) as? T
    }
}