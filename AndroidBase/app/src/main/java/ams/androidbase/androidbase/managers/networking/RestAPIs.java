package ams.androidbase.androidbase.managers.networking;

import java.util.List;

import ams.androidbase.androidbase.models.Country;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestAPIs {
    @GET("all")
    // @Headers("X-Auth-Token:" + BuildConfig.Auth_Token)
    Single<List<Country>> getWorldCountries();
}