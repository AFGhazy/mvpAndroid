package com.blink22.android.mvpandroid.todos;

import com.blink22.android.mvpandroid.BasePresenter;
import com.blink22.android.mvpandroid.BaseView;
import com.blink22.android.mvpandroid.models.Todo;

import java.util.ArrayList;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public interface TodosContract {
    interface View extends BaseView<Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void onGetTodosSuccess(ArrayList<Todo> todos);

    }

    interface Presenter extends BasePresenter {

        void getTodos();

        void updateTodo(Todo todo);

    }
}
