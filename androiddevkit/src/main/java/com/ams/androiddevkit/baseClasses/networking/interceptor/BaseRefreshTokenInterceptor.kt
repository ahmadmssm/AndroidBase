package com.ams.androiddevkit.baseClasses.networking.interceptor

import com.ams.androiddevkit.utils.services.logging.LoggingService
import com.ams.androiddevkit.utils.services.serialization.SerializationService
import com.ams.androiddevkit.utils.services.session.SessionService
import com.ams.jwt.JwtUtils
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

// Ref: https://stackoverflow.com/questions/35516626/android-retrofit2-refresh-oauth-2-token
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRefreshTokenInterceptor<RefreshTokenResponseModel>(protected val serializationService: SerializationService,
                                                                      protected var sessionService: SessionService,
                                                                      protected val loggingService: LoggingService,
                                                                      protected val clazz: Class<RefreshTokenResponseModel>): Interceptor {

    @Suppress("PrivatePropertyName", "PropertyName")
    protected val TAG = "HTTP_INTERCEPTOR"
    protected val semaphore = Semaphore(1)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response { 
        loggingService.d(TAG, "intercept request")
        var originalRequest: Request = chain.request()
        val originalRequestBuilder = originalRequest.newBuilder()
        val oldToken = sessionService.getAccessToken()
        // Log current token
        loggingService.d("$TAG Authorization", originalRequest.header("Authorization") + " VALUE")
        if (shouldAuthenticateRequest(originalRequest)) {
            setAuthenticationHeader(originalRequestBuilder, oldToken)
            // Overwrite old request
            originalRequest = originalRequestBuilder.build()
            val responseOfFirstRequest = chain.proceed(originalRequest)
            // perform all refresh token request in sync blocks, to avoid multiply token updates
            semaphore.acquire()
            loggingService.d(TAG, "Semaphore Acquire")
            val firstRequestStatusCode = responseOfFirstRequest.code
            if(JwtUtils.isTokenExpired(oldToken) || isUnAuthenticated(firstRequestStatusCode)) {
                refreshTokenFor(originalRequest)
            }
            sessionService.getAccessToken()?.let {
                if (oldToken != it) {
                    val modifiedRequestBuilder = chain.request().newBuilder()
                    setAuthenticationHeader(modifiedRequestBuilder, it)
                    return chain.proceed(modifiedRequestBuilder.build())
                }
            }
        }
        // Return original request's response.
        return chain.proceed(originalRequest.newBuilder().build())
    }

    @Throws(IOException::class)
    protected fun refreshTokenFor(originalRequest: Request) {
        val okHttpClient = getOkHttpBuilder().build()
        val refreshTokenRequest = getRefreshTokenRequestBuilder(originalRequest).build()
        val refreshTokenRequestExecutor = okHttpClient.newCall(refreshTokenRequest)
        try {
            val refreshTokenResponse = refreshTokenRequestExecutor.execute()
            if (refreshTokenResponse.code == 200) {
                refreshTokenResponse.body?.string()?.let {
                    val token = extractToken(serializationService.fromJson(it, clazz))
                    loggingService.d("$TAG New Token", token)
                    sessionService.saveAccessToken(token)
                }
            }
            else if (refreshTokenResponse.code in  400..599) {
                // Means that the token was not updated -> Logout
                loggingService.d(TAG, "return unauthorized response $refreshTokenResponse")
                sessionService.removeSession()
            }
        }
        catch (e: IOException) {
            loggingService.e("$TAG TOKEN_INTERCEPTOR ", e.message)
        }
        finally {
            loggingService.d(TAG, "Semaphore Released")
            semaphore.release()
        }
    }

    protected open fun getOkHttpBuilder(): OkHttpClient.Builder {
        val timeOut = getDefaultTimeOut()
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
        if (isDebuggable())
            okHttpBuilder.addInterceptor(getOkHttpLoggingInterceptor())
        return okHttpBuilder
    }

    protected open fun isUnAuthenticated(statusCode: Int): Boolean {
        if (statusCode == 401)
            loggingService.d(TAG, "UNAUTHORIZED $statusCode")
        return statusCode == 401
    }

    protected open fun shouldAuthenticateRequest(request: Request): Boolean {
        return sessionService.hasValidSession()
    }

    protected open fun getRefreshTokenRequestBuilder(originalRequest: Request): Request.Builder {
        val baseURL = getBaseURL(originalRequest)
        return Request
            .Builder()
            .url(baseURL + getRefreshTokenEndpoint())
            .method("POST", getRefreshTokenRequestBody())
    }

    protected open fun getBaseURL(originalRequest: Request): String {
        return originalRequest.url.scheme + "://" + originalRequest.url.host + "/"
    }

    protected open fun getOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        // Set desired logging level -> Default is Full
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return  loggingInterceptor
    }

    protected open fun setAuthenticationHeader(builder: Request.Builder, token: String?) {
        // Add Auth token to each request if authorized
        if (token != null && token.isNotEmpty())
            builder.header("Authorization", String.format("Bearer %s", token))
    }

    // Seconds.
    protected open fun getDefaultTimeOut(): Int = 10

    protected open fun getRefreshTokenRequestBody(): RequestBody? = "".toRequestBody()

    protected abstract fun isDebuggable(): Boolean

    protected abstract fun getRefreshTokenEndpoint():  String

    protected abstract fun extractToken(tokenResponse: RefreshTokenResponseModel): String
}