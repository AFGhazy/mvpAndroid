package com.blink22.android.mvpandroid.service;

/**
 * Created by ahmedghazy on 7/31/18.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blink22.android.mvpandroid.service.AccountAuthenticator;

/**
 * This is used only by Android to run our {@link AccountAuthenticator}.
 */
public class AuthenticatorService extends Service {
    private AccountAuthenticator authenticator;


    @Override
    public void onCreate() {
        this.authenticator = new AccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}