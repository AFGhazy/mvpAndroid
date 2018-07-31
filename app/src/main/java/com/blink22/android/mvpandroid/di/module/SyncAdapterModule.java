package com.blink22.android.mvpandroid.di.module;

import com.blink22.android.mvpandroid.data.db.SyncAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ahmedghazy on 7/31/18.
 */

@Module
public class SyncAdapterModule {
    SyncAdapter mSyncAdapter;

    public SyncAdapterModule(SyncAdapter syncAdapter) {
        mSyncAdapter = syncAdapter;
    }

    @Provides
    SyncAdapter provideSyncAdapter() {
        return mSyncAdapter;
    }
}
