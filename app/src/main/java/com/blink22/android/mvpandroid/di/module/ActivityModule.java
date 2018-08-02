package com.blink22.android.mvpandroid.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.blink22.android.mvpandroid.di.scope.PerActivity;
import com.blink22.android.mvpandroid.ui.newtodo.NewTodoContract;
import com.blink22.android.mvpandroid.ui.newtodo.NewTodoPresenter;
import com.blink22.android.mvpandroid.ui.todos.TodosContract;
import com.blink22.android.mvpandroid.ui.todos.TodosPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ahmedghazy on 7/30/18.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mAppCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    @Provides
    @Named("activity_context")
    Context provideContext() { return mAppCompatActivity; }

    @Provides
    AppCompatActivity provideActivity() { return mAppCompatActivity; }

    @Provides
    CompositeDisposable provideCompositeDisposable() { return new CompositeDisposable(); }

    @Provides
    @PerActivity
    TodosContract.Presenter<TodosContract.View> provideTodosPresenter(TodosPresenter<TodosContract.View> todosPresenter) { return todosPresenter; }

    @Provides
    @PerActivity
    NewTodoContract.Presenter<NewTodoContract.View> provideNewTodoPresenter(NewTodoPresenter<NewTodoContract.View> newTodoPresenter) { return newTodoPresenter; }
}
