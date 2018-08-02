package com.blink22.android.mvpandroid.data.prefs;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public interface PrefsHelper {
    boolean isSyncDone();

    void setSyncDone(boolean syncRequired);
}
