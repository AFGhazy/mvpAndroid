package com.blink22.android.mvpandroid.di.component;

import android.app.Application;
import android.content.Context;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.di.module.ApplicationModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ahmedghazy on 7/30/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application application);

    @Named("application_context")
    Context context();

    Application application();

    DataManager dataManager();
}
