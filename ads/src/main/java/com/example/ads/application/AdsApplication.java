package com.example.ads.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.example.ads.Admod;
import com.example.ads.AppOpenManager;
import com.example.util.AdjustTLA;
import com.example.util.AppUtil;

import java.util.List;

public abstract class AdsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admod.getInstance().init(this, getListTestDeviceId());

        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }
        if (enableAdjust()) {
            setupIdEvent();
            setupAdjust();
        }
    }

    public abstract boolean enableAdsResume();

    public abstract List<String> getListTestDeviceId();

    public abstract String getOpenAppAdId();

    public abstract Boolean buildDebug();

    private void setupIdEvent() {
        AdjustTLA.enableAdjust = true;
    }

    private void setupAdjust() {

        String environment = buildDebug() ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        Log.i("Application", "setupAdjust: " + environment);
        AdjustConfig config = new AdjustConfig(this, getAdjustToken(), environment);

        // Change the log level.
        config.setLogLevel(LogLevel.VERBOSE);
        config.setOnAttributionChangedListener(attribution -> {
            Log.d("AdjustTLA", "Attribution callback called!");
            Log.d("AdjustTLA", "Attribution: " + attribution.toString());
        });

        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener(eventSuccessResponseData -> {
            Log.d("AdjustTLA", "Event success callback called!");
            Log.d("AdjustTLA", "Event success data: " + eventSuccessResponseData.toString());
        });
        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener(eventFailureResponseData -> {
            Log.d("AdjustTLA", "Event failure callback called!");
            Log.d("AdjustTLA", "Event failure data: " + eventFailureResponseData.toString());
        });

        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener(sessionSuccessResponseData -> {
            Log.d("AdjustTLA", "Session success callback called!");
            Log.d("AdjustTLA", "Session success data: " + sessionSuccessResponseData.toString());
        });

        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener(sessionFailureResponseData -> {
            Log.d("AdjustTLA", "Session failure callback called!");
            Log.d("AdjustTLA", "Session failure data: " + sessionFailureResponseData.toString());
        });
        config.setSendInBackground(true);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

    }


    public abstract boolean enableAdjust();


    public abstract String getAdjustToken();


    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }
    }
}
