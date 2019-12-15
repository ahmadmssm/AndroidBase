@file:Suppress("PropertyName")

package com.ams.androiddevkit.baseClasses.globalKeys

/**
 * Created by Ahmad on 23-Nov-16.
 */
open class BasePatterns {
    // Must match
    val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@ [A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    // Must not match
    val SPECIAL_CHARACTERS_PATTERN = "[!#$%^&*()-+=\\/;:\"',><?]"
    //
    fun getOccurrenceOfCharacterPattern(character: String): String {
        return "($character)\u0001*\b"
    }

    // Must match (for the same splitting character)
    // One of the following characters : or . or - or space
    fun get24TimeFormatPattern(splittingCharacter: String): String {
        return "^([0-1]?[0-9]|[2][0-3])$splittingCharacter([0-5][0-9])(:[0-5][0-9])?$"
    }
}