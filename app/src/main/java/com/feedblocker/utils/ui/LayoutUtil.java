package com.feedblocker.utils.ui;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class LayoutUtil
{
    public static void centerPopupInParentWindow(Window window)
    {
        // Get window's root view
        FrameLayout rootView = (FrameLayout) window.getDecorView();

        // Get action bar layout
        View actionBarLayout = rootView.getChildAt(0);

        // Detach it from decor view
        rootView.removeView(actionBarLayout);

        // Create new frame layout
        FrameLayout frameLayout = new FrameLayout(window.getContext());

        // Set params to WRAP_CONTENT
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        // Get 25 as layout margin pixel value
        int layoutMarginPixels = DensityUtil.convertDPToPixels(window.getContext(), 25);

        // Set layout margins
        layoutParams.setMargins(layoutMarginPixels, layoutMarginPixels, layoutMarginPixels, layoutMarginPixels);

        // Set layout gravity to CENTER_VERTICAL
        layoutParams.gravity = Gravity.CENTER_VERTICAL;

        // Apply layout params
        frameLayout.setLayoutParams(layoutParams);

        // Add ActionBar back to frame layout
        frameLayout.addView(actionBarLayout);

        // Add the frame layout to root view
        rootView.addView(frameLayout);
    }
}
