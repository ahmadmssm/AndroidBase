package com.ams.androiddevkit.utils.extensions

inline fun <reified T> List<*>.toListOfType(): List<T>? {
    if(this.itemOfType<T>()) {
        return this as List<T>
    }
    return null
}

inline fun <reified T> List<*>.itemOfType(): Boolean {
    return this.any { it is T }
}