package com.ams.androiddevkit.baseClasses.networking.interceptor

import com.ams.androiddevkit.baseClasses.globalKeys.AndroidDevKitConstants
import com.ams.androiddevkit.utils.services.logging.LoggingService
import com.ams.androiddevkit.utils.services.sharedpreferences.SharedPrefService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.Semaphore

//https://gist.github.com/alex-shpak/da1e65f52dc916716930
@Suppress("MemberVisibilityCanBePrivate")
abstract class TokenInterceptor protected constructor(private val sharedPrefService: SharedPrefService,
                                                      private val loggingService: LoggingService): Interceptor {
    private val mSemaphore = Semaphore(1)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        loggingService.d(
            TAG,
            "intercept request"
        )
        var request = chain.request()
        //Build new request
        val builder = buildRequest(request)
        val token: String = sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)!!
        //save token of this request for future
        loggingService.v("Authorization", request.header("Authorization") + " VALUE")
        if (request.header("Authorization") == null) {
            setAuthHeader(builder, token) //write current token to request
        }
        addAdditionalHeader(builder)
        request = builder.build() //overwrite old request
        val response =
            chain.proceed(request) //perform request, here original request will be executed
        if (response.code == 401) { //if unauthorized
            try {
                loggingService.d(
                    TAG,
                    "UNAUTHORIZED 401"
                )
                mSemaphore.acquire()
                loggingService.d(
                    TAG,
                    "mSemaphore.acquire()"
                )
                //perform all 401 in sync blocks, to avoid multiply token updates
                val currentToken: String = sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN) !!
                //setup currently stored token
                if (currentToken == token) { //compare current token with token that was stored before, if it was not updated - do update
                    loggingService.d(TAG, "token did not change needs refreshing")
                    sharedPrefService.removeKey(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN)
                    loggingService.d(TAG, "clear token because im unauthorized")
                    val code = refreshToken() / 100 //refresh token
                    if (code != 2) { //if refresh token failed for some reason
//                        if (code == 4) { //only if response is 400, 500 might mean that token was not updated
//                            logout(); //go to login screen
//                        }
//                        if (code == 5) //only if response is 400, 500 might mean that token was not updated
//                            logout(); //go to login screen
                        loggingService.v(
                            TAG,
                            "return unauthorized response$response"
                        )
                        return response //if token refresh failed - show error to user
                    }
                }
                if (sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN) != null) { //retry requires new auth token,
                    setAuthHeader(builder, sharedPrefService.getString(AndroidDevKitConstants.PREFERENCE_ACCESS_TOKEN))
                    //set auth token to updated
                    loggingService.d(TAG, "update token header")
                    request = builder.build()
                    return chain.proceed(request) //repeat request with new token
                }
            } catch (e: InterruptedException) {
                loggingService.e("TOKEN_INTERCEPTOR", e.message)
            } finally {
                mSemaphore.release()
            }
        }
        return response
    }

    protected open fun setAuthHeader(builder: Request.Builder, token: String?) {
        // Add Auth token to each request if authorized
        if (token != null && token.isNotEmpty()) builder.header("Authorization", String.format("Bearer %s", token))
    }

    protected abstract fun refreshToken(): Int
    protected open fun addAdditionalHeader(builder: Request.Builder) {}
    protected open fun getLoggingService(): LoggingService { return loggingService }
    protected open fun getSharedPrefService(): SharedPrefService { return sharedPrefService }
    protected open fun buildRequest(request: Request): Request.Builder { return request.newBuilder() }

    companion object {
        private const val TAG = "HTTP_INTERCEPTOR"
    }
}