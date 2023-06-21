package com.hst.offline.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.DecelerateInterpolator;
import androidx.core.app.NotificationCompat;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.hst.offline.data.base.BasePresenter;
import com.hst.offline.ui.main.MainActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.List;

public class SplashPresenter extends BasePresenter<SplashView> {
    Activity mActivity;
    
    public void initView(final Activity activity, final RoundCornerProgressBar progressBar) {
        this.mActivity = activity;
        TedPermission.with(activity).setPermissionListener(new PermissionListener() {
            public void onPermissionGranted() {
                progressBar.setProgress(0.0f);
                progressBar.setMax(100.0f);
                ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, NotificationCompat.CATEGORY_PROGRESS, 0.0f, 100.0f);
                animation.setDuration(700);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
                animation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.finish();
                    }
                });
            }

            public void onPermissionDenied(List<String> list) {
                activity.finish();
            }
        }).setPermissions("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").check();
    }

    public void loadAds() {
        //MobileAds.initialize(this.mActivity);
       // AdmobAds.initFullAds(this.mActivity);
       // AudienceNetworkAds.initialize(this.mActivity);
        //FacebookAds.initFullAds(this.mActivity);

    }
}
