@file:Suppress("PropertyName")

package com.ams.androiddevkit.baseClasses.globalKeys

/**
 * Created by Ahmad Mahmoud on 22-Feb-18.
 */
// Ref : https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android
open class BaseDateAndTimeFormats {
    // Day
    val DAY = "dd"
    // Month
    val MONTH_NUMBER = "MM"
    val MONTH_ABBREVIATION = "MMM"
    val MONTH_NAME = "MMMM"
    // Year
    val YEAR = "YY"
    val YEAR_NUMBER = "YYYY"
    // Hours
    val _12HOURS = "hh"
    val _24HOURS = "HH"
    // Minutes
    val MINUTES = "mm"
    // Seconds
    val SECONDS = "ss"
    // AM/PM
    val AM_PM = "a"
    // Full formats
    //  Time
    val _12Hours_FORMAT = "hh:mm:ss aa"
    val _24Hours_FORMAT = "HH:mm:ss"
    // Date & time
    val DATE_AND_TIME = "hh:mm:ss a dd/MM/yyyy"
    //
    val GREGORIAN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
}