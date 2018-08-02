package com.blink22.android.mvpandroid.ui.todos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.ui.todos.TodosAdapter;
import com.blink22.android.mvpandroid.data.db.model.Todo;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosVH extends RecyclerView.ViewHolder {

    @BindView(R.id.todo_title) TextView mTitle;
    @BindView(R.id.todo_description) TextView mDescription;
    @BindView(R.id.todo_checkbox) CheckBox mDone;
    TodosAdapter.OnItemClickListener mListener;
    Todo mTodo;
    @OnClick(R.id.todo_checkbox)
    void check() {
        mListener.onClick(mTodo);
    }

    public TodosVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Todo todo, final TodosAdapter.OnItemClickListener listener) {
        mTitle.setText(todo.getTitle());
        mDescription.setText(todo.getDescription());
        mDone.setChecked(todo.isDone());
        mListener = listener;
        mTodo = todo;
    }
}
