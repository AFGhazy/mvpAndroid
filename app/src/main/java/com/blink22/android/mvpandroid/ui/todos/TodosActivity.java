package com.blink22.android.mvpandroid.ui.todos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.blink22.android.mvpandroid.BaseApp;
import com.blink22.android.mvpandroid.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosActivity extends BaseActivity{
    @Inject TodosContract.Presenter<TodosContract.View> mPresenter;

    @Override
    protected Fragment createFragment() {
        return TodosFragment.newInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getLifecycle().removeObserver(mPresenter);
    }

    public TodosContract.Presenter<TodosContract.View> getPresenter() {
        return mPresenter;
    }
}
