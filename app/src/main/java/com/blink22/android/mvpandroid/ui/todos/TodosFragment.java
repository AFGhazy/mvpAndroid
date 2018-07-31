package com.blink22.android.mvpandroid.ui.todos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blink22.android.mvpandroid.adapters.TodosAdapter;

import java.util.ArrayList;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosFragment extends Fragment implements TodosContract.View {
    @BindView(R.id.todos_list) RecyclerView mTodos;
    @BindView(R.id.todos_progress) ProgressBar mProgressBar;

    public static TodosFragment newInstance() {

        Bundle args = new Bundle();

        TodosFragment fragment = new TodosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = TodosActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.todos_fragment, container, false);

        ButterKnife.bind(this, v);

        ((TodosActivity) getActivity()).getPresenter().onAttach(this);

        mTodos.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TodosActivity) getActivity()).getPresenter().getTodos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showWait() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Toast.makeText(getActivity().getApplicationContext(), appErrorMessage,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetTodosSuccess(ArrayList<Todo> todos) {
        Log.i(TAG, "here");
        Log.i(TAG, todos.size() + "");
        TodosAdapter adapter = new TodosAdapter(getActivity().getApplicationContext(), todos,
                new TodosAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Todo item) {
                        ((TodosActivity) getActivity()).getPresenter().updateTodo(item);
                    }
                });

        mTodos.setAdapter(adapter);
    }
}
