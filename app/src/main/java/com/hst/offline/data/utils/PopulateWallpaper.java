package com.hst.offline.data.utils;

import android.content.Context;
import java.io.IOException;
import java.util.Objects;

public class PopulateWallpaper {
    public static String[] getList(Context context, String folder) {
        if (Objects.equals(folder, "wallpaper/Anime") || Objects.equals(folder, "wallpaper/Gif")) {
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
