package com.blink22.android.mvpandroid.di.component;

import com.blink22.android.mvpandroid.data.db.SyncAdapter;
import com.blink22.android.mvpandroid.di.PerSyncService;
import com.blink22.android.mvpandroid.di.module.SyncAdapterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ahmedghazy on 7/31/18.
 */

@PerSyncService
@Component(dependencies = ApplicationComponent.class, modules = SyncAdapterModule.class)
public interface SyncAdapterComponent {
    void inject(SyncAdapter syncAdapter);
}
