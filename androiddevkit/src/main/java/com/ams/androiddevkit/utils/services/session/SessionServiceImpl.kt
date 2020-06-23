package com.ams.androiddevkit.utils.services.session

import com.ams.androiddevkit.baseClasses.globalKeys.AndroidDevKitConstants
import com.ams.androiddevkit.utils.services.logging.LoggingService
import com.ams.androiddevkit.utils.services.serialization.SerializationService
import com.ams.androiddevkit.utils.services.sharedpreferences.SharedPrefService
import java.io.IOException

@Suppress("MemberVisibilityCanBePrivate", "PrivatePropertyName")
open class SessionServiceImpl(protected val sharedPrefService: SharedPrefService,
                              protected val loggingService: LoggingService,
                              protected val serializationService: SerializationService): SessionService {

    @Suppress("PropertyName")
    protected val LOG_TAG = "SESSION_HANDLER"

    override fun hasValidUser(): Boolean {
        return sharedPrefService.containsKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)
    }

    override fun hasValidSession(): Boolean {
        return sharedPrefService.containsKey(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)
    }

    override fun getAccessToken(): String? {
        return sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)
    }

    override fun getRefreshToken(): String? {
        return sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN)
    }

    override fun removeSession() {
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_LANGUAGE)
    }

    override fun saveSession(accessToken: String, userId: String){
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID, userId)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN, accessToken)
    }

    override fun saveSession(accessToken: String, refreshToken: String, userId: String) {
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN, refreshToken)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID, userId)
        saveSession(accessToken, userId)
    }

    override fun <T: IUser> saveUser(user: T, clazz: Class<T>) {
        val userString: String = serializationService.toJson(user, clazz)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER, userString)
    }

    override fun saveAccessToken(accessToken: String) {
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN, accessToken)
    }

    override fun saveRefreshTokenToken(refreshToken: String) {
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN, refreshToken)
    }

    override fun <T: IUser> getSavedUser(clazz: Class<T>): T? {
        if (sharedPrefService.containsKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)) {
            try {
                val userString = sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)
                if (userString != null) {
                    loggingService.v(LOG_TAG, "Get Current User: $userString")
                    return serializationService.fromJson(userString, clazz)
                }
                return null
            }
            catch (e: IOException) {
                loggingService.e(LOG_TAG, e.message)
                return null
            }
        }
        return null
    }

    override fun getUserId(): Long? {
        return sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID)?.toLong() ?: run { 0L }
    }
}