package com.ads.control.ads.application;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.ads.control.ads.Admod;
import com.ads.control.ads.AppOpenManager;
import com.ads.control.util.AppUtil;

import java.util.List;

public abstract class AdsMultiDexApplication extends MultiDexApplication {
    public static String TAG = "AdjustTLApp";

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admod.getInstance().init(this, getListTestDeviceId());
        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }
    }


    public abstract boolean enableAdsResume();

    public abstract List<String> getListTestDeviceId();

    public abstract String getOpenAppAdId();

    public abstract Boolean buildDebug();

}
