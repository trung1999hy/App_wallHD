package com.hst.offline.data.widgets;

import android.content.Context;
import android.graphics.Typeface;

public class FontChanger {
    public static Typeface getFont(Context mContext) {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans.ttf");
    }
}
