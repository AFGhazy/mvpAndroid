package com.blink22.android.mvpandroid.newTodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.network.NetworkManager;
import com.blink22.android.mvpandroid.network.TodosSubscriber;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public class NewTodoFragment extends Fragment implements NewTodoContract.View {

    private static final String TAG = NewTodoFragment.class.getSimpleName();

    @BindView(R.id.new_todo_title)
    TextView mTitle;
    @BindView(R.id.new_todo_description)
    TextView mDescription;
    @BindView(R.id.new_todo_submit)
    Button mSubmit;
    NewTodoContract.Presenter mPresenter;

    public static NewTodoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewTodoFragment fragment = new NewTodoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(NewTodoPresenter.getInstance(new TodosSubscriber(NetworkManager.getInstance().getTodosService()), this));
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void setPresenter(NewTodoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mPresenter);
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_new_todo, container, false);
        ButterKnife.bind(this, v);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.createTodo();
            }
        });
        return v;
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
    public void terminate() {
        try {
            getActivity().finish();
        } catch (NullPointerException e) {
            Log.i(TAG, e.getMessage());
        }

    }
}
