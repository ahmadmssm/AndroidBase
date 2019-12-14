package com.ams.androiddevkit.baseClasses.networking.mock

import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer

/**
 * This class is to define the requests that should get filtered and served by the mock server.
 * @param path the path part of the request url. The default is null
 * @param httpMethod the httpMethod of the request. The default is GET
 * @param body the request body. Cannot be used together with GET requests. The default is null
 * @param headers the http headers to filter. The default is empty headers
 */
data class RequestFilter(
    val path: String? = null,
    val httpMethod: HttpMethod = HttpMethod.GET,
    val body: String? = null,
    val headers: Headers = Headers.headersOf()
) {

    companion object {

        fun from(request: Request) =
                RequestFilter(
                        path = request.url.encodedPath,
                        httpMethod = getMethodOrDefault(request.method),
                        body = request.body?.asString(),
                        headers = request.headers
                )

        private fun getMethodOrDefault(method:String) =
                try {
                    HttpMethod.valueOf(method)
                }
                catch (e:IllegalArgumentException) {
                    HttpMethod.GET
                }
    }
}

fun RequestBody.asString(): String {
    val buffer = Buffer()
    writeTo(buffer)
    return buffer.readUtf8()
}
