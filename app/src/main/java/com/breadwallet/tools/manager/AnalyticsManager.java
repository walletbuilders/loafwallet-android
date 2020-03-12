package com.breadwallet.tools.manager;

import android.content.Context;
import android.os.Bundle;
import com.breadwallet.tools.util.CustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Litewallet
 * Created by Kerry Washington on 3/11/20
 * email: mrkerrywashington@icloud.com
 * Copyright © 2020 Litecoin Foundation. All rights reserved.
 */

public final class AnalyticsManager {

    private static FirebaseAnalytics instance;

    private AnalyticsManager() {
        // NO-OP
    }

    public static void init(Context context) {
        instance = FirebaseAnalytics.getInstance(context);
    }

    public static void logCustomEvent(CustomEvent customEvent) {
        instance.logEvent(customEvent.toString(), null);
    }

    public static void logEvent(String eventString) {
        instance.logEvent(eventString, null);
    }

    public static void trackEvent(String event, Bundle bundle) {
        instance.logEvent(event, bundle);
    }
}