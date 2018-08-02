package com.blink22.android.mvpandroid.ui.todos;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.service.SyncAdapter;
import com.blink22.android.mvpandroid.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosPresenter<V extends TodosContract.View> extends BasePresenter<V>
        implements TodosContract.Presenter<V> {
    private static final String TAG = TodosPresenter.class.getSimpleName();

    @Inject
    public TodosPresenter(@Named("application_context") Context context, DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(context, dataManager, compositeDisposable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        super.onCreate();
        if(!getDataManager().isSyncDone()) {
            SyncAdapter.performSync(getAppContext());
            getDataManager().setSyncDone(true);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        getTodos();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestry() {
        super.onDestroy();
    }

    @Override
    public void getTodos() {
        if(getView() != null) {
            getView().showWait();
            getCompositeDisposable().add(getDataManager()
                    .getTodos()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            todos -> {
                                getView().removeWait();
                                getView().onGetTodosSuccess(todos);
                            },
                            throwable -> getView().onFailure(throwable.getLocalizedMessage()),
                            () -> {}
                    )
            );
        }
    }

    @Override
    public void updateTodo(Todo todo) {
        todo.setDone(!todo.isDone());
        todo.setUpdatedDate(new Date().toString());
        getCompositeDisposable().add( getDataManager()
                .updateTodo(todo.getId(), todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        retTodo -> {},
                        throwable -> {},
                        () -> {}
                )
        );
    }
}
