package com.ams.androiddevkit.utils

import java.text.SimpleDateFormat
import java.util.*

open class DateTimeUtils {
    open fun isDateBetweenTwoDates(minDate: Date, maxDate: Date, testDate: Date): Boolean {
        // https://stackoverflow.com/questions/883060/how-can-i-determine-if-a-date-is-between-two-dates-in-java
        return testDate.after(minDate) && testDate.before(maxDate)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun getDifferenceBetweenTwoDates(startDate: Date, endDate: Date): Date {
        val dateDifferent = endDate.time - startDate.time
        return Date(dateDifferent)
    }

    // targetDateFormat is the format of your startDate & endDate arguments and the the format of the target date output
    open fun getDifferenceBetweenTwoDates(startDate: Date,
                                     endDate: Date,
                                     targetDateFormat: String,
                                     locale: Locale = Locale.getDefault()): String {
        // https://stackoverflow.com/questions/21285161/android-difference-between-two-dates
        val resultDate = getDifferenceBetweenTwoDates(startDate, endDate)
        val formatter = SimpleDateFormat(targetDateFormat, locale)
        return formatter.format(resultDate)
    }
}