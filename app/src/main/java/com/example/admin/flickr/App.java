package com.example.admin.flickr;

import android.app.Application;
import android.content.Context;

import com.example.admin.flickr.feed.FlickrApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private FlickrApi flickrApi;

    public FlickrApi getAPI() {
        if (flickrApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.flickr.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            flickrApi = retrofit.create(FlickrApi.class);
        }
        return flickrApi;
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }
}
