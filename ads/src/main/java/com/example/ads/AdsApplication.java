package com.example.ads;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEventFailure;
import com.adjust.sdk.AdjustEventSuccess;
import com.adjust.sdk.AdjustSessionFailure;
import com.adjust.sdk.AdjustSessionSuccess;
import com.adjust.sdk.LogLevel;
import com.adjust.sdk.OnAttributionChangedListener;
import com.adjust.sdk.OnEventTrackingFailedListener;
import com.adjust.sdk.OnEventTrackingSucceededListener;
import com.adjust.sdk.OnSessionTrackingFailedListener;
import com.adjust.sdk.OnSessionTrackingSucceededListener;

import java.util.List;

public abstract class AdsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

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


    private void setupIdEvent() {
        AdjustApero.enableAdjust = true;
    }

    private void setupAdjust() {

        String environment = BuildConfig.DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        Log.i("Application", "setupAdjust: " + environment);
        AdjustConfig config = new AdjustConfig(this, getAdjustToken(), environment);

        // Change the log level.
        config.setLogLevel(LogLevel.VERBOSE);
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                Log.d("AdjustApero", "Attribution callback called!");
                Log.d("AdjustApero", "Attribution: " + attribution.toString());
            }
        });

        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
            @Override
            public void onFinishedEventTrackingSucceeded(AdjustEventSuccess eventSuccessResponseData) {
                Log.d("AdjustApero", "Event success callback called!");
                Log.d("AdjustApero", "Event success data: " + eventSuccessResponseData.toString());
            }
        });
        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
            @Override
            public void onFinishedEventTrackingFailed(AdjustEventFailure eventFailureResponseData) {
                Log.d("AdjustApero", "Event failure callback called!");
                Log.d("AdjustApero", "Event failure data: " + eventFailureResponseData.toString());
            }
        });

        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
            @Override
            public void onFinishedSessionTrackingSucceeded(AdjustSessionSuccess sessionSuccessResponseData) {
                Log.d("AdjustApero", "Session success callback called!");
                Log.d("AdjustApero", "Session success data: " + sessionSuccessResponseData.toString());
            }
        });

        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
            @Override
            public void onFinishedSessionTrackingFailed(AdjustSessionFailure sessionFailureResponseData) {
                Log.d("AdjustApero", "Session failure callback called!");
                Log.d("AdjustApero", "Session failure data: " + sessionFailureResponseData.toString());
            }
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
