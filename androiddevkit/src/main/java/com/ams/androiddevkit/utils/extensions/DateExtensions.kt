package com.ams.androiddevkit.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun Date.isBetweenTwoDates(minDate: Date, maxDate: Date): Boolean {
    // https://stackoverflow.com/questions/883060/how-can-i-determine-if-a-date-is-between-two-dates-in-java
    return this.after(minDate) && this.before(maxDate)
}