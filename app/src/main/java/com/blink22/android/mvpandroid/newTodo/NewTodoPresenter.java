package com.blink22.android.mvpandroid.newTodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.blink22.android.mvpandroid.models.Todo;
import com.blink22.android.mvpandroid.network.TodosSubscriber;
import com.blink22.android.mvpandroid.todos.TodosContract;
import com.blink22.android.mvpandroid.todos.TodosPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoPresenter implements NewTodoContract.Presenter {
    private static final String TAG = TodosPresenter.class.getSimpleName();
    private static NewTodoPresenter sNewTodoPresenter;

    private final TodosSubscriber mTodosSubscriber;
    private final NewTodoContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public static NewTodoPresenter getInstance(TodosSubscriber todosSubscriber, NewTodoContract.View view) {
        return sNewTodoPresenter == null ? new NewTodoPresenter(todosSubscriber, view) : sNewTodoPresenter;
    }

    private NewTodoPresenter(TodosSubscriber todosSubscriber, NewTodoContract.View view) {

        this.mTodosSubscriber = todosSubscriber;
        this.mView = view;

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void start() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stop() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void createTodo() {

        Todo todo = new Todo();
        todo.setTitle(mView.getTitle());
        todo.setDetails(mView.getDescription());

        DisposableObserver<Todo> disposableObserver = mTodosSubscriber.createTodo(new TodosSubscriber.Callback<Todo>() {
            @Override
            public void onSuccess(Todo todo) {
                mView.terminate();
            }

            @Override
            public void onError(Exception e) {

            }

        }, todo);

        mCompositeDisposable.add(disposableObserver);

    }
}
