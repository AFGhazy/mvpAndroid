package com.blink22.android.mvpandroid.data.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class AppPrefsHelper implements PrefsHelper {
    SharedPreferences mSharedPreferences;

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";

    @Inject
    public AppPrefsHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public String getUserName() {
        return mSharedPreferences.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setUserName(String userName) {
        mSharedPreferences.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }
}
