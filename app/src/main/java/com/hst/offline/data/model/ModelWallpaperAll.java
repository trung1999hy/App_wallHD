package com.hst.offline.data.model;

public class ModelWallpaperAll {
    public String wallpaper_folder;
    public String wallpaper_image;

    public ModelWallpaperAll(String wallpaper_image2, String wallpaper_folder2) {
        this.wallpaper_image = wallpaper_image2;
        this.wallpaper_folder = wallpaper_folder2;
    }

    public ModelWallpaperAll(String wallpaper_image2) {
        this.wallpaper_image = wallpaper_image2;
    }
}
