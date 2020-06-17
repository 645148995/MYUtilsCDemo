package com.ctvit.utils;

import android.app.Application;
import android.content.Context;

import com.ctvit.utils.app.CtvitConfig;

import java.lang.ref.WeakReference;

public class MyApplication extends Application {

    private static WeakReference<Context> mWeakReferenceContext;

    @Override
    public void onCreate() {
        super.onCreate();
        CtvitConfig.getInstance().init(this);
    }

    public static Context getContext() {
        if (mWeakReferenceContext == null) {
            throw new IllegalArgumentException("mWeakReferenceContext is null");
        }
        return mWeakReferenceContext.get().getApplicationContext();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTrimMemory(int level) {//程序在内存清理的时候执行
        super.onTrimMemory(level);

    }

    @Override
    public void onLowMemory() {//低内存的时候执行
        super.onLowMemory();
    }
}
