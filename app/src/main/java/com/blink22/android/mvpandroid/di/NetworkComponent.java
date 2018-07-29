package com.blink22.android.mvpandroid.di;

import com.blink22.android.mvpandroid.newTodo.NewTodoActivity;
import com.blink22.android.mvpandroid.newTodo.NewTodoFragment;
import com.blink22.android.mvpandroid.todos.TodosActivity;
import com.blink22.android.mvpandroid.todos.TodosFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ahmedghazy on 7/29/18.
 */

@Singleton
@Component(modules={AppModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(TodosFragment todosFragment);
    void inject(NewTodoFragment newTodoFragment);
}
