package com.blink22.android.mvpandroid.di.module;

import android.content.Context;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.service.SyncAdapter;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ahmedghazy on 7/31/18.
 */

@Module
public class SyncAdapterModule {
    @Inject
    SyncAdapter mSyncAdapter;

    public SyncAdapterModule(SyncAdapter syncAdapter) {
        mSyncAdapter = syncAdapter;
    }

    @Provides
    SyncAdapter provideSyncAdapter() {
        return mSyncAdapter;
    }
}
