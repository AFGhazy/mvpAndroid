package com.blink22.android.mvpandroid.data.db;

import com.blink22.android.mvpandroid.data.db.model.Todo;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public interface DbHelper {

    Observable<Todo> saveTodo(Todo todo);

    Observable<Todo> updateTodo(int id, Todo todo);

    Observable<ArrayList<Todo>> getTodos();

    Observable<Boolean> deleteTodos();

}
