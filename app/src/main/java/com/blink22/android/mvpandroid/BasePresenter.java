package com.blink22.android.mvpandroid;

import android.arch.lifecycle.LifecycleObserver;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public interface BasePresenter extends LifecycleObserver{

    void start();

    void stop();
}
