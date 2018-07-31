package com.blink22.android.mvpandroid.data.db.model;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class Todo extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private boolean done;

    public void set(Todo todo) {
        setTitle(todo.getTitle());
        setDescription(todo.getDescription());
        setDone(todo.isDone());
    }

    private static final AtomicInteger ai = new AtomicInteger();

    public Todo() {
        id = ai.addAndGet(1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
