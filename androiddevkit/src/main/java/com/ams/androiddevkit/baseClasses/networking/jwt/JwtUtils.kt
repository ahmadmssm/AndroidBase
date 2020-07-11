package com.ams.androiddevkit.baseClasses.networking.jwt

import android.util.Base64
import android.util.Log
import com.ams.jwt.JwtWrapper
import java.nio.charset.Charset
import java.util.*

object JwtUtils {

    @Suppress("MemberVisibilityCanBePrivate")
    fun getExpirationDate(token: String?): Long? {
        token?.split("\\.")?.let {
            if (it.size > 1) {
                val header = getDecodedToken(it[0])
                val body = getDecodedToken(it[1])
                Log.d("DECODED JWT ", "Header: $header")
                Log.d("DECODED JWT ", "Body: $body")
                body?.let {
                    val expiryDate = JwtWrapper(body).exp
                    return expiryDate?.toLongOrNull()
                } ?: run { return null }
            }
        } ?: kotlin.run {
            getDecodedToken(token)?.let {
                val expiryDate = JwtWrapper(it).exp
                return expiryDate?.toLongOrNull()
            } ?: run { return null }
        }
        return null
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getDecodedToken(token: String?): String? {
        return try {
            val data: ByteArray = Base64.decode(token, Base64.DEFAULT)
            String(data, Charset.forName("UTF-8"))
        }
        catch(e: RuntimeException) {
            null
        }
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


