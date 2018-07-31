package com.blink22.android.mvpandroid.ui.newtodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.blink22.android.mvpandroid.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoActivity extends BaseActivity {

    @Inject
    NewTodoContract.Presenter<NewTodoContract.View> mPresenter;

    @Override
    protected Fragment createFragment() {
        return NewTodoFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getLifecycle().removeObserver(mPresenter);
    }

    public NewTodoContract.Presenter getPresenter() {
        return mPresenter;
    }
}
