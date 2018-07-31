package com.blink22.android.mvpandroid.data.api;

import com.blink22.android.mvpandroid.data.db.model.Todo;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class AppApiHelper implements ApiHelper {
    ApiHelper mApiHelper;

    @Inject
    public AppApiHelper(Retrofit retrofit) {
        mApiHelper = retrofit.create(ApiHelper.class);
    }

    @Override
    public Observable<ArrayList<Todo>> getTodos() {
        return mApiHelper.getTodos();
    }

    @Override
    public Observable<Todo> saveTodo(Todo todo) {
        return mApiHelper.saveTodo(todo);
    }

    @Override
    public Observable<Todo> updateTodo(int id, Todo todo) {
        return mApiHelper.updateTodo(id, todo);
    }
}
