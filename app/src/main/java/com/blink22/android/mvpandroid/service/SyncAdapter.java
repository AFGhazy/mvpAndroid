package com.blink22.android.mvpandroid.service;

/**
 * Created by ahmedghazy on 7/31/18.
 */

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.blink22.android.mvpandroid.BaseApp;
import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.di.scope.PerSyncService;
import com.blink22.android.mvpandroid.di.component.DaggerSyncAdapterComponent;
import com.blink22.android.mvpandroid.di.component.SyncAdapterComponent;
import com.blink22.android.mvpandroid.di.module.SyncAdapterModule;
import com.blink22.android.mvpandroid.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Named;


@PerSyncService
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    @Inject DataManager mDataManager;

    public SyncAdapter(Context c, boolean autoInit) {
        super(c, autoInit);
        initialize(c);
    }

    public SyncAdapter(Context c, boolean autoInit, boolean parallelSync) {
        super(c, autoInit, parallelSync);
        initialize(c);

    }

    void initialize(Context c) {
        SyncAdapterComponent component = DaggerSyncAdapterComponent.builder()
                .applicationComponent(((BaseApp)c.getApplicationContext()).getComponent())
                .syncAdapterModule(new SyncAdapterModule(this))
                .build();
        component.inject(this);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        mDataManager.syncData();
    }

    public static void performSync(Context c) {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountGeneral.getAccount(c),
                c.getResources().getString(R.string.content_authority), b);
    }
}