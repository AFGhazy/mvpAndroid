package com.blink22.android.mvpandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.models.Todo;
import com.blink22.android.mvpandroid.viewHolders.TodosVH;

import java.util.ArrayList;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosAdapter extends RecyclerView.Adapter<TodosVH> {

    private final OnItemClickListener mListener;
    private ArrayList<Todo> mData;
    private Context mContext;

    public TodosAdapter(Context context, ArrayList<Todo> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
        mContext = context;
    }

    @Override
    public TodosVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todos, null);
        return new TodosVH(view);
    }

    @Override
    public void onBindViewHolder(TodosVH holder, int position) {
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
