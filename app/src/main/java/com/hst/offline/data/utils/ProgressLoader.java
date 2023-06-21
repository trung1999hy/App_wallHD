package com.hst.offline.data.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import com.hst.offline.R;

public class ProgressLoader extends AlertDialog {
    public ProgressLoader(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    public void show() {
        super.show();
        setContentView(R.layout.progress_layout);
    }

    public void cancel() {
        super.cancel();
    }

    public void stopLoader() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ProgressLoader.this.dismiss();
            }
        }, 500);
    }

    public void dismiss() {
        super.dismiss();
    }
}
