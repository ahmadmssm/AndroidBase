package com.ams.androidkitapplication.managers.networking;

import com.ams.androidkitapplication.models.Country;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestAPIs {
    @GET("all")
    // @Headers("X-Auth-Token:" + BuildConfig.Auth_Token)
    Single<List<Country>> getWorldCountries();
}