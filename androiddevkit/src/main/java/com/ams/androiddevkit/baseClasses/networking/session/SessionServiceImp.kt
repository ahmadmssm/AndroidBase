package com.ams.androiddevkit.baseClasses.networking.session

import androidx.lifecycle.LiveData
import com.ams.androiddevkit.baseClasses.baseModels.IUser
import com.ams.androiddevkit.baseClasses.globalKeys.AndroidDevKitConstants
import com.ams.androiddevkit.utils.services.serialization.SerializationService
import com.ams.androiddevkit.utils.services.sharedpreferences.SharedPrefService
import com.ams.androiddevkit.utils.sharedPreference.reactiveSharedPreference.LiveSharedPreferences

class SessionServiceImp(private val sharedPrefService: SharedPrefService,
                        private val serializationService: SerializationService): SessionService {

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

    /**
     * We should nullify the user first before delete it from shared preference because if we listen
     * to  the shared preference changes and we delete the user from it first the listener will
     * listen that the user changed so he will ask to get the current user again and the getting
     * the current user function in [SessionService] firstly check if the user isn't null so
     * return the member variable and don't get from shred preference so it will return the user
     * that are actually deleted.
     */
    override fun removeSession() { //Save token
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN)
        sharedPrefService.removeValueWithKey(AndroidDevKitConstants.PREFERENCE_LANGUAGE)
    }

    override fun saveSession(accessToken: String, refreshToken: String, user_id: String) {
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_REFRESH_TOKEN, refreshToken)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID, user_id)
        saveSession(accessToken, user_id)
    }

    override fun saveSession(accessToken: String, userId: String) {
        // CrashlyticsLogger.logException(new Exception("Trying to save session with a null context"));
        // Save token
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID, userId)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN, accessToken)
    }

    override fun <T: IUser> saveCurrentUser(user: T, clazz: Class<T>) {
        val userString = serializationService.toJson<T>(user, clazz)
        sharedPrefService.saveString(AndroidDevKitConstants.PREFERENCE_CURRENT_USER, userString)
    }

    override fun <T: IUser> getCurrentUser(clazz: Class<T>): T? {
        if (sharedPrefService.containsKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)) {
            return sharedPrefService.getObject(AndroidDevKitConstants.PREFERENCE_CURRENT_USER, clazz)
        }
        return null
    }

    override fun <T : IUser> observeUserChanges(clazz: Class<T>): LiveData<T>? {
        if (sharedPrefService.containsKey(AndroidDevKitConstants.PREFERENCE_CURRENT_USER)) {
            return LiveSharedPreferences(sharedPrefService.sharedPreferences).getObject(AndroidDevKitConstants.PREFERENCE_CURRENT_USER, clazz)
        }
        return null
    }

    override fun getUserId(): Long {
        return sharedPrefService.getLong(AndroidDevKitConstants.PREFERENCE_CURRENT_USER_ID, -1)!!
    }
}