package com.hst.offline.data.utils;

import com.hst.offline.data.model.ModelWallpaperAll;
import java.io.Serializable;
import java.util.ArrayList;

public class Variable implements Serializable {
    public static ArrayList<ModelWallpaperAll> arrayListDetail = new ArrayList<>();
    public static final String assets_folder = "file:///android_asset/";
    public static int interstitial_count = 0;
    public static boolean native_loaded = false;
}
