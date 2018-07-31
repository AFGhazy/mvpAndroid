package com.blink22.android.mvpandroid.ui.newtodo;

import com.blink22.android.mvpandroid.di.PerActivity;
import com.blink22.android.mvpandroid.ui.base.IBasePresenter;
import com.blink22.android.mvpandroid.ui.base.IBaseView;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public interface NewTodoContract {
    interface View extends IBaseView {

        String getTitle();
        String getDescription();
        void terminate();

    }

    @PerActivity
    interface Presenter<V extends View> extends IBasePresenter<V> {

        public void createTodo();

    }
}
