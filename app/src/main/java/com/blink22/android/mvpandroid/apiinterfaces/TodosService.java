package com.blink22.android.mvpandroid.apiinterfaces;

import com.blink22.android.mvpandroid.models.Todo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public interface TodosService {

    @GET("/todos")
    Observable<ArrayList<Todo>> getTodos();

    @POST("/todos")
    Observable<Todo> createTodo(@Body Todo todo);

    @PUT("/todos/{id}")
    Observable<Todo> updateTodo(@Path("id") int id, @Body Todo todo);
}
