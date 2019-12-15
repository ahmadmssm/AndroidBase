package com.ams.androiddevkit.utils.extensions

fun String.isValidPasswordPattern(passwordPattern: String): Boolean {
    val regex = Regex(passwordPattern)
    return this.matches(regex)
}

fun String.isValidMail(): Boolean {
    val pattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@ [A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val regex = Regex(pattern)
    return this.matches(regex)
}

fun String.containsSpecialCharacter(): Boolean {
    val pattern = "[!#$%^&*()-+=/;:\"',><?]"
    val regex = Regex(pattern)
    return this.matches(regex)
}

fun isValid24Hours(timeString: String, splittingCharacter: String?): Boolean {
    val pattern = "^([0-1]?[0-9]|[2][0-3])$splittingCharacter([0-5][0-9])(:[0-5][0-9])?$"
    val regex = Regex(pattern)
    return timeString.matches(regex)
}

fun String.print() {
    println(this)
}

fun String.printWith(flag: String) {
    println("$flag $this")
}