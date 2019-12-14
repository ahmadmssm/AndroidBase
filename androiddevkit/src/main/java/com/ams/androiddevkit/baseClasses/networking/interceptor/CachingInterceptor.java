//package com.ams.androiddevkit.baseClasses.networking.interceptor;
//
//import androidx.annotation.NonNull;
//
//import com.robusta.bootstrap.service.logging.LoggingService;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class CachingInterceptor implements Interceptor {
//    private LoggingService loggingService;
//
//    public CachingInterceptor(LoggingService loggingService) {
//        this.loggingService = loggingService;
//    }
//
//    @Override
//    public Response intercept(@NonNull Chain chain) throws IOException {
//        loggingService.d("CacheIntercept", "intercept request");
//        final Request request = chain.request();
//        Response originalResponse = chain.proceed(request);
//        // cache if it's GET request with 2XX response
//        if (hasCacheHeader(request)
//                && request.method().equals("GET")
//                && withOkCode(originalResponse)) {
//            int maxStale = 30 * 60;
//            loggingService.d("CacheIntercept", "will cache request");
//            return originalResponse.newBuilder().header("Cache-Control", "public, max-age=" + maxStale + ", max-stale=" + maxStale).build();
//        } else {
//            loggingService.d("CacheIntercept", "won't cache request");
//        }
//        return originalResponse;
//    }
//
//    private boolean hasCacheHeader(Request request) {
//        final String header = request.header("Cache-Control");
//        return header != null && header.equals("cache");
//    }
//
//    private boolean withOkCode(Response originalResponse) {
//        return (originalResponse.code() / 100) == 2;
//    }
//}
