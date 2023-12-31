package com.hst.hdwallpaper.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import androidx.preference.PreferenceManager;

public class Pref {
    private static final String TAG = "shared";
    private static Pref sPref;
    private final SharedPreferences preferences;

    private Pref(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized Pref getSharedPref(Context context) {
        Pref pref;
        synchronized (Pref.class) {
            Log.d(TAG, Thread.currentThread().getName());
            if (sPref == null) {
                Log.d(TAG, Thread.currentThread().getName());
                sPref = new Pref(context);
            }
            pref = sPref;
        }
        return pref;
    }

    public boolean write(String key, String value) {
        Editor editor = this.preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean write(String key, boolean value) {
        Editor editor = this.preferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean write(String key, int value) {
        Editor editor = this.preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean write(String key, float value) {
        Editor editor = this.preferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public boolean write(String key, long value) {
        Editor editor = this.preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public String read(String key) {
        return this.preferences.getString(key, "");
    }

    public long readLong(String key) {
        return this.preferences.getLong(key, 0);
    }

    public int readInt(String key) {
        return this.preferences.getInt(key, 0);
    }

    public float readFloat(String key) {
        return this.preferences.getFloat(key, 0.0f);
    }

    public boolean readBoolean(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public boolean readBooleanDefaultTrue(String key) {
        return this.preferences.getBoolean(key, true);
    }

    public boolean contains(String key) {
        return this.preferences.contains(key);
    }
}
