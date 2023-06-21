package com.hst.offline;

import android.app.Application;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;

import com.hst.offline.util.Preference;

public class App extends Application {
    private static Preference preference = null;
    private static App application = null;
    public static Preference getInstance() {
        if (preference == null) {
            preference = Preference.buildInstance(application);
        }
        preference.isOpenFirst();
        return preference;
    }
    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new Builder().build());
        application = this;
    }
}
