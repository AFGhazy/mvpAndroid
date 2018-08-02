package com.blink22.android.mvpandroid.data;

import android.content.SyncResult;

import com.blink22.android.mvpandroid.data.api.ApiHelper;
import com.blink22.android.mvpandroid.data.db.DbHelper;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.data.prefs.PrefsHelper;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public interface DataManager extends PrefsHelper, ApiHelper{

    void syncData();

}
