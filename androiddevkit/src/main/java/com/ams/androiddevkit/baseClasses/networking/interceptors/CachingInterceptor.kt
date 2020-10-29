package com.ams.androiddevkit.baseClasses.networking.interceptors

import com.ams.androiddevkit.utils.services.logging.LoggingService
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CachingInterceptor(private val loggingService: LoggingService): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        loggingService.d("CacheIntercept", "intercept request")
        val request = chain.request()
        val originalResponse = chain.proceed(request)
        // cache if it's GET request with 2XX response
        if (hasCacheHeader(request) && request.method == "GET" && withOkCode(originalResponse)) {
            val maxStale = 30 * 60
            loggingService.d("CacheIntercept", "will cache request")
            return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxStale, max-stale=$maxStale")
                .build()
        } else loggingService.d("CacheIntercept", "won't cache request")
        return originalResponse
    }

    private fun hasCacheHeader(request: Request): Boolean {
        val header = request.header("Cache-Control")
        return header != null && header == "cache"
    }

    private fun withOkCode(originalResponse: Response): Boolean {
        return originalResponse.code / 100 == 2
    }
}