package com.example.util;

import android.content.Context;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAdRevenue;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.applovin.mediation.MaxAd;
import com.google.android.gms.ads.AdValue;

public class AdjustTLA {
    public static boolean enableAdjust = false;
    private static String eventNamePurchase = "";

    public static void setEventNamePurchase(String eventNamePurchase) {
        AdjustTLA.eventNamePurchase = eventNamePurchase;
    }

    public static void trackAdRevenue(String id) {
        AdjustAdRevenue adjustAdRevenue = new AdjustAdRevenue(id);
        Adjust.trackAdRevenue(adjustAdRevenue);
    }

    public static void onTrackEvent(String eventName) {
        AdjustEvent event = new AdjustEvent(eventName);
        Adjust.trackEvent(event);
    }

    public static void onTrackEvent(String eventName, String id) {
        AdjustEvent event = new AdjustEvent(eventName);
        // Assign custom identifier to event which will be reported in success/failure callbacks.
        event.setCallbackId(id);
        Adjust.trackEvent(event);
    }

    public static void onTrackRevenue(String eventName, float revenue, String currency) {
        AdjustEvent event = new AdjustEvent(eventName);
        // Add revenue 1 cent of an euro.
        event.setRevenue(revenue/ 1000000.0, currency);
        Adjust.trackEvent(event);
    }

    public static void onTrackRevenuePurchase(float revenue, String currency) {
        if (AdjustTLA.enableAdjust) {
            onTrackRevenue(eventNamePurchase, revenue, currency);
        }
    }

    public static void pushTrackEventAdmod(  AdValue adValue) {
        if (AdjustTLA.enableAdjust) {
            AdjustAdRevenue adRevenue = new AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB);
            adRevenue.setRevenue(adValue.getValueMicros() / 1000000.0, adValue.getCurrencyCode());

            Adjust.trackAdRevenue(adRevenue);
        }
    }
    public static void pushTrackEventApplovin(MaxAd ad, Context context) {
        if (AdjustTLA.enableAdjust) {
            AdjustAdRevenue adjustAdRevenue = new AdjustAdRevenue( AdjustConfig.AD_REVENUE_APPLOVIN_MAX );
            adjustAdRevenue.setRevenue( ad.getRevenue(), "USD" );
            adjustAdRevenue.setAdRevenueNetwork( ad.getNetworkName() );
            adjustAdRevenue.setAdRevenueUnit( ad.getAdUnitId() );
            adjustAdRevenue.setAdRevenuePlacement( ad.getPlacement() );

            Adjust.trackAdRevenue( adjustAdRevenue );

            FirebaseAnalyticsUtil.logPaidAdImpression(context,ad);
        }
    }
}
