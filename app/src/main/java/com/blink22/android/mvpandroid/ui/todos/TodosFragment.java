package com.blink22.android.mvpandroid.ui.todos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.db.model.Todo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosFragment extends Fragment implements TodosContract.View {
    private static final String TAG = TodosActivity.class.getSimpleName();
    @BindView(R.id.todos_list) RecyclerView mTodos;
    @BindView(R.id.todos_progress) ProgressBar mProgressBar;
    private TodosPresenter<TodosContract.View> mTodosPresenter;
    private TodosAdapter mTodosAdapter;

    public static TodosFragment newInstance() {
        return new TodosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTodosPresenter = ((TodosActivity) getActivity()).getPresenter();
        mTodosAdapter = TodosAdapter.newInstance(getActivity(), mTodosPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.todos_fragment, container, false);
        ButterKnife.bind(this, v);
        mTodosPresenter.onAttach(this);
        mTodos.setAdapter(mTodosAdapter);
        mTodos.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mTodosAdapter.onTodosUpdate(todos);
        mTodos.setAdapter(mTodosAdapter);
    }
}
