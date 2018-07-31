package com.blink22.android.mvpandroid.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.blink22.android.mvpandroid.data.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ahmedghazy on 7/30/18.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V>, LifecycleObserver {

    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;
    private V mView;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        mDataManager = dataManager;
        mCompositeDisposable = compositeDisposable;
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

    public void onAttach(V view) {
        mView = view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
