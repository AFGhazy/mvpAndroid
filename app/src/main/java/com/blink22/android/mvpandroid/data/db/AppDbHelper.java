package com.blink22.android.mvpandroid.data.db;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.blink22.android.mvpandroid.data.db.model.Todo;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class AppDbHelper implements DbHelper, LifecycleObserver {
    Realm mRealm;

    @Inject
    public AppDbHelper(Realm realm) {
        mRealm = realm;
    }

    @Override
    public Observable<Todo> saveTodo(final Todo todo) {

        Observable<Todo> observable = Observable.create(new ObservableOnSubscribe<Todo>() {
            @Override
            public void subscribe(final ObservableEmitter<Todo> emitter) throws Exception {
                try {
                    final Realm currentRealm = Realm.getDefaultInstance();
                    currentRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(todo);
                            emitter.onNext(todo);
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        return observable;
    }

    @Override
    public Observable<Todo> updateTodo(final int id, final Todo todo) {
            final Observable<Todo> observable = Observable.create(new ObservableOnSubscribe<Todo>() {
                @Override
                public void subscribe(final ObservableEmitter<Todo> emitter) throws Exception {
                    try {
                        final Realm currentRealm = Realm.getDefaultInstance();
                        currentRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Todo gTodo = realm.where(Todo.class).equalTo("id", id).findFirst();
                                gTodo.set(todo);
                                emitter.onNext(gTodo);
                            }
                        });
                    }
                    catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            });

            return observable;
    }

    @Override
    public Observable<ArrayList<Todo>> getTodos() {


       Observable<ArrayList<Todo>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<Todo>>() {
            @Override
            public void subscribe(final ObservableEmitter<ArrayList<Todo>> emitter) throws Exception {
                try {
                    final Realm currentRealm = Realm.getDefaultInstance();
                    currentRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<Todo> realmResults = realm.where(Todo.class).findAll();
                            emitter.onNext(new ArrayList<Todo>(realm.copyFromRealm(realmResults)));
                            emitter.onComplete();
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        return observable;
    }
}
