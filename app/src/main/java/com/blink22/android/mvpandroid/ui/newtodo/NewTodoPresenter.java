package com.blink22.android.mvpandroid.ui.newtodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.ui.base.BasePresenter;
import com.blink22.android.mvpandroid.ui.todos.TodosPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoPresenter<V extends NewTodoContract.View> extends BasePresenter<V>
        implements NewTodoContract.Presenter<V> {
    private static final String TAG = TodosPresenter.class.getSimpleName();

    @Inject
    public NewTodoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager,compositeDisposable);
    }

    @Override
    public void createTodo() {

        Todo todo = new Todo();
        todo.setTitle(getView().getTitle());
        todo.setDescription(getView().getDescription());

        getCompositeDisposable().add(  getDataManager().saveTodo(todo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Todo>() {
            @Override
            public void onNext(Todo todo) {
                getView().terminate();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }) );

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }
}
