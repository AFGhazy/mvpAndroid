package com.blink22.android.mvpandroid.newTodo;

import com.blink22.android.mvpandroid.BasePresenter;
import com.blink22.android.mvpandroid.BaseView;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public interface NewTodoContract {
    interface View extends BaseView<Presenter> {

        String getTitle();
        String getDescription();
        void terminate();

    }

    interface Presenter extends BasePresenter {

        public void createTodo();

    }
}
