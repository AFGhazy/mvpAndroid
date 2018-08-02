package com.blink22.android.mvpandroid.data;

import android.content.SyncResult;

import com.blink22.android.mvpandroid.data.api.ApiHelper;
import com.blink22.android.mvpandroid.data.db.DbHelper;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.data.prefs.PrefsHelper;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

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
    public void syncData() {
        mApiHelper.getTodos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    apiTodos -> {
                        mDbHelper.getTodos()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    dbTodos -> {
                                        syncMissingAndUpdatedVersionOfTodos(dbTodos, apiTodos, mApiHelper);
                                        syncMissingAndUpdatedVersionOfTodos(apiTodos, dbTodos, mDbHelper);
                                    },
                                    throwable -> {},
                                    () -> {}

                                );
                    },
                    throwable -> {},
                    () -> {});
    }

    @Override
    public boolean isSyncDone() {
        return mPrefsHelper.isSyncDone();
    }

    @Override
    public void setSyncDone(boolean syncRequired) {
        mPrefsHelper.setSyncDone(syncRequired);
    }

    private void syncMissingAndUpdatedVersionOfTodos(ArrayList<Todo> sourceArr, ArrayList<Todo> destinationArr, ApiHelper destination) {
        for(Todo srcTodo: sourceArr) {
            boolean mostRecent = false;
            boolean found = false;
            for(Todo destTodo: destinationArr) {
                if(srcTodo.getId() == destTodo.getId()) {
                    found = true;
                    if (new Date(srcTodo.getUpdatedDate()).compareTo(new Date(destTodo.getUpdatedDate())) > 0) {
                        mostRecent = true;
                    }
                }
            }

            if(!found) {
                destination.saveTodo(srcTodo).subscribeOn(Schedulers.io()).subscribe();
            }
            else {
                if(mostRecent) {
                    destination.updateTodo(srcTodo.getId(), srcTodo).subscribeOn(Schedulers.io()).subscribe();
                }
            }
        }
    }
}
