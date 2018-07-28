package com.blink22.android.mvpandroid.todos;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.blink22.android.mvpandroid.models.Todo;
import com.blink22.android.mvpandroid.network.TodosSubscriber;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosPresenter implements TodosContract.Presenter {
    private static final String TAG = TodosPresenter.class.getSimpleName();
    private static TodosPresenter sTodosPresenter;

    private final TodosSubscriber mTodosSubscriber;
    private final TodosContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private ArrayList<Todo> mTodos;

    public static TodosPresenter getInstance(TodosSubscriber todosSubscriber, TodosContract.View view) {
        return sTodosPresenter == null ? new TodosPresenter(todosSubscriber, view) : sTodosPresenter;
    }

    private TodosPresenter(TodosSubscriber todosSubscriber, TodosContract.View view) {

        this.mTodosSubscriber = todosSubscriber;
        this.mView = view;

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) public void start() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) public void stop() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void getTodos() {

        mView.showWait();



        DisposableObserver<ArrayList<Todo>> disposableObserver = mTodosSubscriber.getTodos(new TodosSubscriber.Callback<ArrayList<Todo>>() {
            @Override
            public void onSuccess(ArrayList<Todo> todos) {
                mView.removeWait();
                mTodos = todos;
                mView.onGetTodosSuccess(todos);
            }

            @Override
            public void onError(Exception networkError) {
                mView.removeWait();
                mView.onFailure(networkError.getMessage());
            }

        });

        mCompositeDisposable.add(disposableObserver);
    }

    @Override
    public void updateTodo(Todo todo) {
        todo.setDone(!todo.isDone());

        DisposableObserver<Todo> disposableObserver = mTodosSubscriber.updateTodo(new TodosSubscriber.Callback<Todo>() {
            @Override
            public void onSuccess(Todo todo) {

            }

            @Override
            public void onError(Exception networkError) {

            }

        }, todo);

        mCompositeDisposable.add(disposableObserver);
    }
}
