package com.hst.hdwallpaper.ui.utils;

import android.content.Context;
import java.io.IOException;
import java.util.Objects;

public class IPopulateWallpaper {
    public static String[] getList(Context context, String folder) {
        if (Objects.equals(folder, "wallpaper/anime") || Objects.equals(folder, "wallpaper/gif")) {
            return null;
        }
        try {
            return context.getAssets().list(folder);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
