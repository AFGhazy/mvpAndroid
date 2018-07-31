package com.blink22.android.mvpandroid.di.component;

import com.blink22.android.mvpandroid.di.PerActivity;
import com.blink22.android.mvpandroid.di.module.ActivityModule;
import com.blink22.android.mvpandroid.ui.newtodo.NewTodoActivity;
import com.blink22.android.mvpandroid.ui.todos.TodosActivity;

import dagger.Component;

/**
 * Created by ahmedghazy on 7/30/18.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(TodosActivity todosActivity);

    void inject(NewTodoActivity newTodoActivity);
}
