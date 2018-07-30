package com.blink22.android.mvpandroid.models;

import android.widget.Adapter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class Todo{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("details")
    private String details;

    @SerializedName("done")
    private boolean done;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
