package com.ams.androiddevkit.baseClasses.networking.session

import androidx.lifecycle.LiveData
import com.ams.androiddevkit.baseClasses.baseModels.IUser

interface SessionService {
    fun hasValidUser(): Boolean
    fun hasValidSession(): Boolean
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getUserId(): Long
    fun removeSession()
    fun saveSession(accessToken: String, refreshToken: String, user_id: String)
    fun saveSession(accessToken: String, userId: String)
    fun <T: IUser> saveCurrentUser(user: T, clazz: Class<T>)
    fun <T: IUser> getCurrentUser(clazz: Class<T>): T?
    fun <T: IUser>observeUserChanges(clazz: Class<T>): LiveData<T>?
}