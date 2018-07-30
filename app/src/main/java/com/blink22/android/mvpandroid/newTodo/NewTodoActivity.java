package com.blink22.android.mvpandroid.newTodo;

import android.support.v4.app.Fragment;

import com.blink22.android.mvpandroid.BaseActivity;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return NewTodoFragment.newInstance();
    }
}
