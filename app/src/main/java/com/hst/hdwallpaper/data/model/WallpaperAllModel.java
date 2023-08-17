package com.hst.hdwallpaper.data.model;

public class WallpaperAllModel {
    public String wallpaper_folder;
    public String wallpaper_image;

    public WallpaperAllModel(String wallpaper_image2, String wallpaper_folder2) {
        this.wallpaper_image = wallpaper_image2;
        this.wallpaper_folder = wallpaper_folder2;
    }

    public WallpaperAllModel(String wallpaper_image2) {
        this.wallpaper_image = wallpaper_image2;
    }
}
