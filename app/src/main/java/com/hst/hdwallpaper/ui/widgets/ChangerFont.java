package com.hst.hdwallpaper.ui.widgets;

import android.content.Context;
import android.graphics.Typeface;

public class ChangerFont {
    public static Typeface getFont(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans.ttf");
    }
}
