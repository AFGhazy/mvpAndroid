package com.blink22.android.mvpandroid.network;

import com.blink22.android.mvpandroid.apiInterfaces.TodosService;
import com.blink22.android.mvpandroid.models.Todo;


import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class TodosSubscriber {

    private static final String TAG = TodosSubscriber.class.getSimpleName();

    private final TodosService mTodosService;

    public TodosSubscriber(TodosService todosService) {
        mTodosService = todosService;
    }

    public DisposableObserver<ArrayList<Todo>> getTodos(final Callback<ArrayList<Todo>> callback) {

        return mTodosService
                .getTodos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Todo>>() {
                    @Override
                    public void onNext(ArrayList<Todo> todos) {
                        callback.onSuccess(todos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<Todo> createTodo(final Callback<Todo> callback, Todo todo) {

        return mTodosService
                .createTodo(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Todo>() {
                    @Override
                    public void onNext(Todo todo) {
                        callback.onSuccess(todo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public DisposableObserver<Todo> updateTodo(final Callback<Todo> callback, Todo todo) {

        return mTodosService
                .updateTodo(todo.getId(), todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Todo>() {
                    @Override
                    public void onNext(Todo todo) {
                        callback.onSuccess(todo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new Exception(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface Callback<T> {
        void onSuccess(T ret);

        // Investigate what kind of exception
        void onError(Exception e);
    }

}
