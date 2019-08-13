package ams.androidbase.androidbase.managers.networking;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestAPIs {

    @GET("competitions/PL/matches")
    // @Headers("X-Auth-Token:" + BuildConfig.Auth_Token)
    Observable<String> getGitlabProjects(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);

}