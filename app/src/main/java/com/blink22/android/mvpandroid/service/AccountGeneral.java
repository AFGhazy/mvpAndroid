package com.blink22.android.mvpandroid.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ahmedghazy on 7/31/18.
 */

public final class AccountGeneral {

    public static Account getAccount(Context c) {
        return new Account(c.getResources().getString(R.string.content_authority), c.getResources().getString(R.string.account_type));
    }

    public static void createSyncAccount(Context c) {
        // Flag to determine if this is a new account or not
        boolean created = false;

        // Get an account and the account manager
        Account account = getAccount(c);
        AccountManager manager = (AccountManager)c.getSystemService(Context.ACCOUNT_SERVICE);

        // Attempt to explicitly create the account with no password or extra data
        if (manager.addAccountExplicitly(account, null, null)) {
            ContentResolver.setIsSyncable(account, c.getResources().getString(R.string.content_authority), 1);
            ContentResolver.setSyncAutomatically(account, c.getResources().getString(R.string.content_authority), true);
            ContentResolver.addPeriodicSync(account, c.getResources().getString(R.string.content_authority), new Bundle(), AppConstants.APP_SYNC_FREQUENCY);

            created = true;
        }
    }
}