package com.example.admin.flickr.feed;

import com.example.admin.flickr.models.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {
    public static final String API_KEY = "9289d82268ddf830bbd685d1b01d9986";

    @GET("services/rest/")
    Observable<Result> repos(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("format") String format,
            @Query("nojsoncallback") int noJsonCallback);
}
