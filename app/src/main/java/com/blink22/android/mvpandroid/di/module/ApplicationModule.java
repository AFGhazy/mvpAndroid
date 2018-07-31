package com.blink22.android.mvpandroid.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.blink22.android.mvpandroid.BuildConfig;
import com.blink22.android.mvpandroid.data.AppDataManager;
import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.api.ApiEndPoint;
import com.blink22.android.mvpandroid.data.api.ApiHelper;
import com.blink22.android.mvpandroid.data.api.AppApiHelper;
import com.blink22.android.mvpandroid.data.db.AppDbHelper;
import com.blink22.android.mvpandroid.data.db.DbHelper;
import com.blink22.android.mvpandroid.data.prefs.AppPrefsHelper;
import com.blink22.android.mvpandroid.data.prefs.PrefsHelper;
import com.blink22.android.mvpandroid.utils.AppConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmedghazy on 7/30/18.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Named("application_context")
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    SharedPreferences provideSharedPreferences(@Named("shared_pref_name") String sharedPrefsName) {
        return mApplication.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
    }

    @Provides
    @Named("shared_pref_name")
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) { return appApiHelper; }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PrefsHelper providePreferencesHelper(AppPrefsHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }


    @Provides
    @Named("api_url")
    String provideApiLink() {
        return ApiEndPoint.TODOS;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        Cache cache = new Cache(application.getCacheDir(), AppConstants.CACHE_SIZE);
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
    Realm provideRealm() {
        Realm.init(mApplication);
        return Realm.getDefaultInstance();
    }
}
