package com.blink22.android.mvpandroid.ui.newtodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.ui.base.BaseActivity;
import com.blink22.android.mvpandroid.ui.todos.TodosActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoFragment extends Fragment implements NewTodoContract.View {
    private static final String TAG = NewTodoFragment.class.getSimpleName();
    @BindView(R.id.new_todo_title) TextView mTitle;
    @BindView(R.id.new_todo_description) TextView mDescription;
    @BindView(R.id.new_todo_submit) Button mSubmit;
    @OnClick(R.id.new_todo_submit)
    public void submit() {
        mNewTodoPresenter.createTodo();
    }
    private NewTodoContract.Presenter<NewTodoContract.View> mNewTodoPresenter;
    private View mView;

    public static NewTodoFragment newInstance() {
        return new NewTodoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewTodoPresenter = ((NewTodoActivity) getActivity()).getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_new_todo, container, false);
        ButterKnife.bind(this, v);
        mNewTodoPresenter.onAttach(this);
        mView = v;
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }

    @Override
    public String getDescription() {
        return mDescription.getText().toString();
    }

    @Override
    public void showToastWithMessage(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void terminate() {
        try {
            getActivity().finish();
        } catch (NullPointerException e) {

        }
    }
}
