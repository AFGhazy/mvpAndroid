package com.blink22.android.mvpandroid.data;

import android.util.Log;

import com.blink22.android.mvpandroid.data.api.ApiHelper;
import com.blink22.android.mvpandroid.data.db.DbHelper;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.data.prefs.PrefsHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class AppDataManager implements DataManager {
    private ApiHelper mApiHelper;
    private DbHelper mDbHelper;
    private PrefsHelper mPrefsHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper, DbHelper dbHelper, PrefsHelper prefsHelper) {
        mApiHelper = apiHelper;
        mDbHelper = dbHelper;
        mPrefsHelper = prefsHelper;
    }

    @Override
    public String getUserName() {
        return mPrefsHelper.getUserName();
    }

    @Override
    public void setUserName(String userName) {
        mPrefsHelper.setUserName(userName);
    }

    @Override
    public Observable<Todo> saveTodo(Todo todo) {

        return mDbHelper.saveTodo(todo);
    }

    @Override
    public Observable<Todo> updateTodo(int id, Todo todo) {
        return mDbHelper.updateTodo(id, todo);
    }

    @Override
    public Observable<ArrayList<Todo>> getTodos() {
        return mDbHelper.getTodos();

    }

    @Override
    public Observable<ArrayList<Todo>> getDbTodos() {
        return mDbHelper.getTodos();
    }
}
