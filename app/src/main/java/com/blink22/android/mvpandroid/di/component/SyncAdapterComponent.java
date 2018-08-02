package com.blink22.android.mvpandroid.di.component;

import com.blink22.android.mvpandroid.di.scope.PerSyncService;
import com.blink22.android.mvpandroid.di.module.SyncAdapterModule;
import com.blink22.android.mvpandroid.service.SyncAdapter;

import javax.inject.Named;

import dagger.Component;

/**
 * Created by ahmedghazy on 7/31/18.
 */

@PerSyncService
@Component(dependencies = ApplicationComponent.class, modules = SyncAdapterModule.class)
public interface SyncAdapterComponent {

    void inject(SyncAdapter syncAdapter);

}
