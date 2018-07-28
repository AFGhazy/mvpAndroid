package com.blink22.android.mvpandroid.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.adapters.TodosAdapter;
import com.blink22.android.mvpandroid.models.Todo;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosVH extends RecyclerView.ViewHolder {

    @BindView(R.id.todo_title) TextView mTitle;
    @BindView(R.id.todo_description) TextView mDescription;
    @BindView(R.id.todo_checkbox) CheckBox mDone;

    public TodosVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Todo todo, final TodosAdapter.OnItemClickListener listener) {
        mTitle.setText(todo.getTitle());
        mDescription.setText(todo.getDetails());
        mDone.setChecked(todo.isDone());
        mDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(todo);
            }
        });
    }
}
