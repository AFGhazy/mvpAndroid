package com.blink22.android.mvpandroid.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.service.AccountGeneral;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V>, LifecycleObserver {

    private final Context mAppContext;
    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;
    private V mView;

    @Inject
    public BasePresenter(@Named("application_context") Context appContext, DataManager dataManager, CompositeDisposable compositeDisposable) {
        mAppContext = appContext;
        mDataManager = dataManager;
        mCompositeDisposable = compositeDisposable;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        AccountGeneral.createSyncAccount(mAppContext);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        mCompositeDisposable.dispose();
        mView = null;
    }

    public void onAttach(V view) {
        mView = view;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public V getView() {
        return mView;
    }

    public Context getAppContext() {
        return mAppContext;
    }
}
