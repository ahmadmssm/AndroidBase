package com.ams.jwt

import android.util.Base64
import java.nio.charset.Charset
import java.util.*

object JwtUtils {

    @Suppress("MemberVisibilityCanBePrivate")
    fun getExpirationDate(token: String?): Long? {
        val data: ByteArray = Base64.decode(token, Base64.DEFAULT)
        val decodedJwtString = String(data, Charset.forName("UTF-8"))
        val expiryDate = JwtWrapper(decodedJwtString).exp
        return expiryDate?.toLongOrNull()
    }

    fun isTokenExpired(token: String?): Boolean {
        getExpirationDate(token)?.let {
            val currentDate = Date()
            val tokenExpirationDate = Date(it * 1000)
            return tokenExpirationDate.before(currentDate)
        }
        return true
    }
}


