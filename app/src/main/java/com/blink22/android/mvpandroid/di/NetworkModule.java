package com.blink22.android.mvpandroid.di;

import android.app.Application;

import com.blink22.android.mvpandroid.BuildConfig;
import com.blink22.android.mvpandroid.apiInterfaces.TodosService;
import com.blink22.android.mvpandroid.network.TodosSubscriber;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmedghazy on 7/29/18.
 */

@Module
public class NetworkModule {
    @Provides
    @Named("api_url")
    String provideApiLink() {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 5 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    @Named("cached")
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    @Named("non_cached")
    OkHttpClient provideOkHttpClientNonCached() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    @Provides
    @Singleton
    @Named("rxjava2_call_adapter")
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named("cached") OkHttpClient okHttpClient,
                             @Named("rxjava2_call_adapter") CallAdapter.Factory rxJava2CallAdapterFactory,
                             @Named("api_url") String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(url)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    TodosService provideTodosService(Retrofit retrofit) {
        return retrofit.create(TodosService.class);
    }

    @Provides
    @Singleton
    TodosSubscriber provideTodosSubscriber(TodosService todosService) {
        return new TodosSubscriber(todosService);
    }
}
