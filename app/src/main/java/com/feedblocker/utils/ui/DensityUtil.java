package com.feedblocker.utils.ui;

import android.content.Context;

public class DensityUtil {
    public static int convertDPToPixels(Context context, int dps) {
        // Get display scale
        float scale = context.getResources().getDisplayMetrics().density;

        // Calculate pixels using scale
        return (int) (dps * scale + 0.5f);
    }
}
