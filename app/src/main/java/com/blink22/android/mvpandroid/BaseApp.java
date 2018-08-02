package com.blink22.android.mvpandroid;

import android.app.Application;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.di.component.ApplicationComponent;
import com.blink22.android.mvpandroid.di.component.DaggerApplicationComponent;
import com.blink22.android.mvpandroid.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/29/18.
 */

public class BaseApp extends Application {

    @Inject DataManager mDataManager;
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
