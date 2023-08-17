package com.hst.hdwallpaper.ui.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IHelpers {
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 4);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static String getExt(String filePath) {
        int strLength = filePath.lastIndexOf(".");
        if (strLength > 0) {
            return filePath.substring(strLength + 1).toLowerCase();
        }
        return "";
    }

    private static boolean isMiUi() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    private static String getSystemProperty(String propName) {
        BufferedReader input = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String sb = "getprop " + propName;
            input = new BufferedReader(new InputStreamReader(runtime.exec(sb).getInputStream()), 1024);
            String line = input.readLine();
            input.close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        } catch (IOException e2) {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        }
    }
}
