package com.blink22.android.mvpandroid.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ahmedghazy on 7/31/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerSyncService {
}
