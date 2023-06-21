package com.hst.offline.data.utils;

import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

public class Logger {
    public static void log(String string) {
        Log.wtf("CODERTUBE :", string);
    }

    public static void log(int string) {
        Log.wtf("CODERTUBE :", String.valueOf(string));
    }

    public static void log(boolean string) {
        Log.wtf("CODERTUBE :", String.valueOf(string));
    }

    public static void log(long string) {
        Log.wtf("CODERTUBE :", String.valueOf(string));
    }

    public static void log(byte[] string) {
        Log.wtf("CODERTUBE :", Arrays.toString(string));
    }

    public static void log(Uri string) {
        Log.wtf("CODERTUBE :", String.valueOf(string));
    }
}
