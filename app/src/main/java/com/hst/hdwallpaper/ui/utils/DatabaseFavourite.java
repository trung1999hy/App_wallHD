package com.hst.hdwallpaper.ui.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hst.hdwallpaper.data.model.WallpaperAllModel;
import java.util.ArrayList;


@SuppressLint("Recycle")
public class DatabaseFavourite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favourite_wallpaper";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE_URL = "wallpaper_image";
    private static final String TABLE_NAME = "tbl_favorite_wallpaper";

    public DatabaseFavourite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_favorite_wallpaper(id INTEGER PRIMARY KEY,wallpaper_image TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_favorite_wallpaper");
        onCreate(db);
    }

    public void AddtoFavorite(WallpaperAllModel wallpaperAllModel) {
        SQLiteDatabase queryStr = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_URL, wallpaperAllModel.wallpaper_image);
        queryStr.insert(TABLE_NAME, null, values);
        queryStr.close();
    }

    public ArrayList<WallpaperAllModel> getAllData() {
        ArrayList<WallpaperAllModel> dataList = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM tbl_favorite_wallpaper ORDER BY id ASC", null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new WallpaperAllModel(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    public void RemoveFav(WallpaperAllModel wallpaperAllModel) {
        SQLiteDatabase queryStr = getWritableDatabase();
        queryStr.delete(TABLE_NAME, "wallpaper_image = ?", new String[]{String.valueOf(wallpaperAllModel.wallpaper_image)});
        queryStr.close();
    }

    public boolean isFavourite(WallpaperAllModel wallpaperAllModel) {
        String queryStr = "SELECT  * FROM tbl_favorite_wallpaper WHERE wallpaper_image='" +
                wallpaperAllModel.wallpaper_image +
                "'";
        Cursor cursor = getWritableDatabase().rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            while (cursor.getString(1) == null) {
                if (!cursor.moveToNext()) {
                    // TODO:
                }
            }
            return true;
        }
        return false;
    }
}
