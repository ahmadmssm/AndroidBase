//package com.ams.androiddevkit.baseClasses.networking.interceptor;
//
//
//import androidx.annotation.NonNull;
//
//import com.robusta.bootstrap.Constants;
//import com.robusta.bootstrap.service.logging.LoggingService;
//import com.robusta.bootstrap.service.sharedpreferences.SharedPrefService;
//
//import java.io.IOException;
//import java.util.concurrent.Semaphore;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
////https://gist.github.com/alex-shpak/da1e65f52dc916716930
//public abstract class TokenInterceptor implements Interceptor {
//    private static final String TAG = "HTTP_INTERCEPTOR";
//    private final Semaphore mSemaphore = new Semaphore(1);
//    @NonNull
//    private SharedPrefService sharedPrefService;
//
//    @NonNull
//    private LoggingService loggingService;
//
//    protected TokenInterceptor(@NonNull SharedPrefService sharedPrefService,
//                               @NonNull LoggingService loggingService) {
//        this.sharedPrefService = sharedPrefService;
//        this.loggingService = loggingService;
//    }
//
//    @Override
//    public Response intercept(@NonNull Chain chain) throws IOException {
//        loggingService.d(TAG, "intercept request");
//        Request request = chain.request();
//
//        //Build new request
//        Request.Builder builder = buildRequest(request);
//        String token = sharedPrefService.getString(Constants.PREFERENCE_ACCESS_TOKEN); //save token of this request for future
//
//        loggingService.v("Authorization", request.header("Authorization") + " VALUE");
//        if (request.header("Authorization") == null) {
//            setAuthHeader(builder, token); //write current token to request
//        }
//        addAdditionalHeader(builder);
//        request = builder.build(); //overwrite old request
//        Response response = chain.proceed(request); //perform request, here original request will be executed
//
//        if (response.code() == 401) { //if unauthorized
//
//            try {
//                loggingService.d(TAG, "UNAUTHORIZED 401");
//
//                mSemaphore.acquire();
//                loggingService.d(TAG, "mSemaphore.acquire()");
//
//                //perform all 401 in sync blocks, to avoid multiply token updates
//                String currentToken = sharedPrefService.getString(Constants.PREFERENCE_ACCESS_TOKEN); //setup currently stored token
//                if (currentToken != null && currentToken.equals(token)) { //compare current token with token that was stored before, if it was not updated - do update
//                    loggingService.d(TAG, "token did not change needs refreshing");
//                    sharedPrefService.removeKey(Constants.PREFERENCE_ACCESS_TOKEN);
//                    loggingService.d(TAG, "clear token because im unauthorized");
//
//                    int code = refreshToken() / 100; //refresh token
//                    if (code != 2) { //if refresh token failed for some reason
////                        if (code == 4) { //only if response is 400, 500 might mean that token was not updated
////                            logout(); //go to login screen
////                        }
////                        if (code == 5) //only if response is 400, 500 might mean that token was not updated
////                            logout(); //go to login screen
//                        loggingService.v(TAG, "return unauthorized response" + response.toString());
//                        return response; //if token refresh failed - show error to user
//                    }
//                }
//                if (sharedPrefService.getString(Constants.PREFERENCE_ACCESS_TOKEN) != null) { //retry requires new auth token,
//                    setAuthHeader(builder, sharedPrefService.getString(Constants.PREFERENCE_ACCESS_TOKEN)); //set auth token to updated
//                    loggingService.d(TAG, "update token header");
//                    request = builder.build();
//                    return chain.proceed(request); //repeat request with new token
//                }
//            } catch (InterruptedException e) {
//                loggingService.e("TOKEN_INTERCEPTOR", e.getMessage());
//            } finally {
//                mSemaphore.release();
//            }
//        }
//
//        return response;
//    }
//
//
//    protected void setAuthHeader(Request.Builder builder, String token) {
//        if (token != null && !token.isEmpty()) //Add Auth token to each request if authorized
//            builder.header("Authorization", String.format("Bearer %s", token));
//    }
//
//    @SuppressWarnings("WeakerAccess")
//    protected abstract int refreshToken();
//
//    @SuppressWarnings("WeakerAccess")
//    protected void addAdditionalHeader(Request.Builder builder) {
//    }
//
//    @NonNull
//    protected LoggingService getLoggingService() {
//        return loggingService;
//    }
//
//    @NonNull
//    protected SharedPrefService getSharedPrefService() {
//        return sharedPrefService;
//    }
//
//    protected Request.Builder buildRequest(Request request) {
//        return request.newBuilder();
//    }
//}
