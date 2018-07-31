package com.blink22.android.mvpandroid.ui.todos;

import android.util.Log;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

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
    public TodosPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getTodos() {

        getView().showWait();


        getCompositeDisposable().add(getDataManager()
                .getTodos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Todo>>() {
                    @Override
                    public void onNext(ArrayList<Todo> todos) {
                        getView().onGetTodosSuccess(todos);
                        getView().removeWait();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onFailure(e.getMessage());
                        Log.i(TAG, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }
            )
        );
    }

    @Override
    public void updateTodo(Todo todo) {
        todo.setDone(!todo.isDone());

        getCompositeDisposable().add( getDataManager()
                .updateTodo(todo.getId(), todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Todo>(

                    ) {
                        @Override
                        public void onNext(Todo todo) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
    }

}
