package com.example.ads.funtion;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

public class AdCallback {
    public void onAdClosed() {
    }

    public void onAdFailedToLoad(LoadAdError i) {
    }

    public void onAdFailedToShow(AdError adError) {
    }

    public void onAdLeftApplication() {
    }


    public void onAdLoaded() {
    }

    public void onInterstitialLoad(InterstitialAd interstitialAd) {

    }

    public void onAdClicked() {
    }

    public void onAdImpression() {
    }


    public void onUnifiedNativeAdLoaded(NativeAd unifiedNativeAd) {

    }
}
