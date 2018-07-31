package com.blink22.android.mvpandroid.ui.base;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.blink22.android.mvpandroid.BaseApp;
import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.data.db.AccountGeneral;
import com.blink22.android.mvpandroid.data.db.DatabaseContract;
import com.blink22.android.mvpandroid.data.db.SyncAdapter;
import com.blink22.android.mvpandroid.di.component.ActivityComponent;
import com.blink22.android.mvpandroid.di.component.DaggerActivityComponent;
import com.blink22.android.mvpandroid.di.module.ActivityModule;
import com.blink22.android.mvpandroid.models.NavigationItemEnum;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected abstract Fragment createFragment();
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private TodosObserver mTodosObserver;

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((BaseApp) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();

        for(NavigationItemEnum item: NavigationItemEnum.values()) {
            MenuItem menuItem = menu.add(R.id.nav_group, item.getId(), Menu.NONE, item.getLabelResourceId());
            menuItem.setIcon(item.getIconResourceId());
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        // Create your sync account
        AccountGeneral.createSyncAccount(this);

        // Perform a manual sync by calling this:
        SyncAdapter.performSync();


        // Setup example content observer
        mTodosObserver = new TodosObserver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register the observer at the start of our activity
        getContentResolver().registerContentObserver(
                DatabaseContract.CONTENT_URI, // Uri to observe (our articles)
                true, // Observe its descendants
                mTodosObserver); // The observer
    }

    @Override
    protected void onStop() {
        super.onStop();


        if (mTodosObserver != null) {
            // Unregister the observer at the stop of our activity
            getContentResolver().unregisterContentObserver(mTodosObserver);
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();

        for(NavigationItemEnum item: NavigationItemEnum.values()) {
            if(item.getId() == menuItem.getItemId()) {
                Intent i = new Intent(getApplicationContext(), item.getClassToLaunch());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }

        return true;
    }

    private void refreshArticles() {
        Log.i(getClass().getName(), "Articles data has changed!");
    }

    private final class TodosObserver extends ContentObserver {
        private TodosObserver() {
            // Ensure callbacks happen on the UI thread
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            // Handle your data changes here!!!
            refreshArticles();
        }
    }
}
