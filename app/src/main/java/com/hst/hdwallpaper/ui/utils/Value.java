package com.hst.hdwallpaper.ui.utils;

import com.hst.hdwallpaper.data.model.WallpaperAllModel;
import java.io.Serializable;
import java.util.ArrayList;

public class Value implements Serializable {
    public static ArrayList<WallpaperAllModel> arrayListDetail = new ArrayList<>();
    public static final String assets_folder = "file:///android_asset/";
    public static int interstitial_count = 0;
    public static boolean native_loaded = false;
}
