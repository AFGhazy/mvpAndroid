package com.blink22.android.mvpandroid.ui.todos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.db.model.Todo;

import java.util.ArrayList;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosAdapter extends RecyclerView.Adapter<TodosVH> {

    private final OnItemClickListener mListener;
    private ArrayList<Todo> mData;

    public static TodosAdapter newInstance(Context c, final TodosPresenter<TodosContract.View> todosPresenter) {
        return new TodosAdapter(c.getApplicationContext(), new ArrayList<Todo>(),
                new TodosAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Todo item) {
                        todosPresenter.updateTodo(item);
                    }
                });
    }

    TodosAdapter(Context context, ArrayList<Todo> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    void onTodosUpdate(ArrayList<Todo> todos) {
        mData = todos;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public TodosVH onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todos, null);
        return new TodosVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodosVH holder, int position) {
        holder.bind(mData.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onClick(Todo item);
    }
}
