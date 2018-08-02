package com.blink22.android.mvpandroid.data.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class AppPrefsHelper implements PrefsHelper {
    SharedPreferences mSharedPreferences;

    private static final String PREF_KEY_SYNC_REQUIRED = "PREF_KEY_SYNC_REQUIRED";

    @Inject
    public AppPrefsHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }


    @Override
    public boolean isSyncDone() {
        return mSharedPreferences.getBoolean(PREF_KEY_SYNC_REQUIRED, false);
    }

    @Override
    public void setSyncDone(boolean syncRequired) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_SYNC_REQUIRED, syncRequired).commit();
    }
}
