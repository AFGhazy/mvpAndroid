package com.blink22.android.mvpandroid.network;

import com.blink22.android.mvpandroid.BuildConfig;
import com.blink22.android.mvpandroid.apiInterfaces.TodosService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();

    private static NetworkManager sNetworkManager;

    private TodosService mTodosService;

    private NetworkManager() {
        // Required private constructor for singleton class
    }

    public static NetworkManager getInstance() {
        if (sNetworkManager == null) {
            sNetworkManager = new NetworkManager();
        }

        return sNetworkManager;
    }

    public TodosService getTodosService() {
        if (mTodosService == null) {


            mTodosService = new Retrofit
                    .Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TodosService.class);
        }

        return mTodosService;
    }
}
