package com.dji.wsbridge.lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.dji.wsbridge.BuildConfig;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import io.fabric.sdk.android.Fabric;

public class BridgeApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "APPLICATION";
    private static BridgeApplication instance;
    private final Bus eventBus = new Bus(ThreadEnforcer.ANY);

    public static BridgeApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DJILogger.init();
        instance = this;
        //registerActivityLifecycleCallbacks(this);
        if (BuildConfig.DEBUG) {
            // Detect UI-Thread blockage
            //BlockCanary.install(this, new AppBlockCanaryContext()).start();
            //// Detect memory leakage
            //if (!LeakCanary.isInAnalyzerProcess(this)) {
            //    LeakCanary.install(this);
            //}
            // Detect thread violation
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDropBox().penaltyLog().build());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyDropBox()
                    .penaltyLog()
                    .build());
        }
    }

    public Bus getBus() {
        return this.eventBus;
    }

    //region -------------------------------------- Activity Callbacks and Helpers ---------------------------------------------
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Created");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Resumed");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Destroyed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Paused");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        DJILogger.v(TAG, activity.getLocalClassName() + " SaveInstance");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Started");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DJILogger.v(TAG, activity.getLocalClassName() + " Stopped");
    }
    //endregion -----------------------------------------------------------------------------------------------------
}
