package com.ams.androiddevkit.baseClasses.networking.interceptor

import com.ams.androiddevkit.BuildConfig
import com.ams.androiddevkit.baseClasses.networking.mock.RequestFilter
import com.ams.androiddevkit.baseClasses.networking.mock.configure
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class OkHttp3MockingInterceptor (
    private val mocks: Map<RequestFilter, MockResponse> = emptyMap(),
    private val mockServer: MockWebServer = MockWebServer().configure()): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        fun findMockResponse(request: Request) = mocks[RequestFilter.from(request)]

        fun Interceptor.Chain.findServer(): HttpUrl =
                when (val mockResponse = findMockResponse(request())) {
                    is MockResponse -> {
                        mockResponse.addHeader(
                                "Mockinizer",
                                " <-- Real request ${request().url} is now mocked to $mockResponse"
                        )
                        mockResponse.addHeader(
                                "server",
                                "Mockinizer ${BuildConfig.VERSION_NAME} by Thomas Fuchs-Martin"
                        )
                        mockServer.enqueue(mockResponse)
                        request().url.newBuilder()
                                .host(mockServer.hostName)
                                .port(mockServer.port)
                                // .scheme("http") // TODO: make http - https configurable
                                .build()
                    }
                    else -> request().url
                }

        return with(chain) {
            proceed(
                    request().newBuilder()
                            .url(findServer())
                            .build()
            )
        }
    }

}
