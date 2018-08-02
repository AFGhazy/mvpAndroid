package com.blink22.android.mvpandroid.ui.newtodo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.ui.base.BasePresenter;
import com.blink22.android.mvpandroid.ui.todos.TodosPresenter;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

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
    public NewTodoPresenter(@Named("application_context") Context context, DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(context, dataManager,compositeDisposable);
    }

    @Override
    public void createTodo() {
        if(getView() != null) {
            Todo todo = new Todo();
            todo.setTitle(getView().getTitle());
            todo.setDescription(getView().getDescription());
            todo.setUpdatedDate(new Date().toString());
            getCompositeDisposable().add(getDataManager()
                    .saveTodo(todo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            retTodo -> {
                                getView().showToastWithMessage(R.string.create_todo_success);
                                getView().terminate();
                            },
                            throwable -> {},
                            () -> {}
                    ));

        }
    }
}
