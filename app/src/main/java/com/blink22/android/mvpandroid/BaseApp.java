package com.blink22.android.mvpandroid;

import android.app.Application;

import com.blink22.android.mvpandroid.di.AppModule;
import com.blink22.android.mvpandroid.di.DaggerNetworkComponent;
import com.blink22.android.mvpandroid.di.NetworkComponent;
import com.blink22.android.mvpandroid.di.NetworkModule;

/**
 * Created by ahmedghazy on 7/29/18.
 */

public class BaseApp extends Application {
    NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();

    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }
}
