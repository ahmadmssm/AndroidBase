package com.ams.androiddevkit.utils.extensions

fun Int.isEvenNumber(): Boolean {
    return (this % 2) == 0
}

fun Int.isOddNumber(): Boolean {
    return !this.isEvenNumber()
}