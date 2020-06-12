package com.ams.androiddevkit.utils.services.session

interface SessionService {

    fun hasValidUser(): Boolean

    fun hasValidSession(): Boolean

    fun getUserId(): Long?

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun saveSession(accessToken: String, userId: String)

    fun saveAccessToken(accessToken: String)

    fun saveRefreshTokenToken(refreshToken: String)

    fun saveSession(accessToken: String, refreshToken: String, userId: String)

    fun removeSession()

    fun <T: IUser> saveUser(user: T, clazz: Class<T>)

    fun <T: IUser> getSavedUser(clazz: Class<T>): T?
}

