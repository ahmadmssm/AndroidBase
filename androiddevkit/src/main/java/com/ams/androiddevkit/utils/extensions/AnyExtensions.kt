package com.ams.androiddevkit.utils.extensions

// https://stackoverflow.com/a/58657456/6927433
inline fun <reified T> Any?.tryCast(block: T.() -> Unit) {
    if (this is T) {
        block()
    }
}

inline fun <reified T> Any?.tryCast(block: T.() -> Unit, fallback: () -> Unit) {
    if (this is T) {
        block()
    }
    else {
        fallback()
    }
}

inline fun <reified T> Any?.tryCastListOf(block: List<T>.() -> Unit) {
    this.tryCast<List<*>> {
        if(this.any { it is T }) {
            block(this as List<T>)
        }
    }
}

inline fun <reified T> Any?.tryCastListOf(block: List<T>.() -> Unit, fallback: () -> Unit) {
    this.tryCast<List<*>> {
        if(this.any { it is T }) {
            block(this as List<T>)
        }
        else {
            fallback()
        }
    }
}