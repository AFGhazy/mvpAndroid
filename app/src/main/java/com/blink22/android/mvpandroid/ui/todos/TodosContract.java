package com.blink22.android.mvpandroid.ui.todos;

import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.di.PerActivity;
import com.blink22.android.mvpandroid.ui.base.IBasePresenter;
import com.blink22.android.mvpandroid.ui.base.IBaseView;

import java.util.ArrayList;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public interface TodosContract {
    interface View extends IBaseView {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void onGetTodosSuccess(ArrayList<Todo> todos);

    }

    @PerActivity
    interface Presenter<V extends View> extends IBasePresenter<V> {

        void getTodos();

        void updateTodo(Todo todo);

    }
}
