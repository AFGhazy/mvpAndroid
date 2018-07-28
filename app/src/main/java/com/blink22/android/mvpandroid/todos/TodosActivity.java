package com.blink22.android.mvpandroid.todos;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blink22.android.mvpandroid.activities.BaseActivity;
import com.blink22.android.mvpandroid.adapters.TodosAdapter;
import com.blink22.android.mvpandroid.models.Todo;

import java.util.ArrayList;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.network.NetworkManager;
import com.blink22.android.mvpandroid.network.TodosSubscriber;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public class TodosActivity extends BaseActivity{

    @Override
    protected Fragment createFragment() {
        return TodosFragment.newInstance();
    }
}
