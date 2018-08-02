package com.blink22.android.mvpandroid.ui.todos;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.blink22.android.mvpandroid.BaseApp;
import com.blink22.android.mvpandroid.service.AccountGeneral;
import com.blink22.android.mvpandroid.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosActivity extends BaseActivity{
    @Inject TodosPresenter<TodosContract.View> mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        getLifecycle().addObserver(mPresenter);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getLifecycle().removeObserver(mPresenter);
    }

    public TodosPresenter<TodosContract.View> getPresenter() {
        return mPresenter;
    }

    @Override
    protected Fragment createFragment() {
        return TodosFragment.newInstance();
    }

}
