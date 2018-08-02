package com.blink22.android.mvpandroid.ui.base;

import android.arch.lifecycle.LifecycleObserver;

/**
 * Created by ahmedghazy on 7/25/18.
 */

public interface IBasePresenter<V extends IBaseView> extends LifecycleObserver{

    void onAttach(V view);

}
