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
                .subscribe(new Observer<ArrayList<Todo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final ArrayList<Todo> apiTodos) {
                        mDbHelper.getTodos()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ArrayList<Todo>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(final ArrayList<Todo> dbTodos) {
                                        for(Todo dbTodo: dbTodos) {

                                            boolean mostRecent = false;
                                            boolean found = false;
                                            for(Todo apiTodo: apiTodos) {
                                                if(dbTodo.getId() == apiTodo.getId()) {
                                                    found = true;
                                                    if (new Date(dbTodo.getUpdatedDate()).compareTo(new Date(apiTodo.getUpdatedDate())) > 0) {
                                                        mostRecent = true;
                                                    }
                                                }
                                            }

                                            if(!found) {
                                                mApiHelper.saveTodo(dbTodo).subscribeOn(Schedulers.io()).subscribe();

                                            }
                                            else {
                                                if(mostRecent) {
                                                    mApiHelper.updateTodo(dbTodo.getId(), dbTodo).subscribeOn(Schedulers.io()).subscribe();
                                                }
                                            }
                                        }

                                        for(Todo apiTodo: apiTodos) {
                                            boolean mostRecent = false;
                                            boolean found = false;
                                            for(Todo dbTodo: dbTodos) {
                                                if(dbTodo.getId() == apiTodo.getId()) {
                                                    found = true;
                                                    if (new Date(apiTodo.getUpdatedDate()).compareTo(new Date(dbTodo.getUpdatedDate())) > 0) {
                                                        mostRecent = true;
                                                    }
                                                }
                                            }

                                            if(!found) {
                                                mDbHelper.saveTodo(apiTodo).subscribeOn(Schedulers.io()).subscribe();
                                            }
                                            else {
                                                if(mostRecent) {
                                                    mDbHelper.updateTodo(apiTodo.getId(), apiTodo).subscribeOn(Schedulers.io()).subscribe();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public boolean isSyncDone() {
        return mPrefsHelper.isSyncDone();
    }

    @Override
    public void setSyncDone(boolean syncRequired) {
        mPrefsHelper.setSyncDone(syncRequired);
    }
}
