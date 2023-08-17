package com.hst.hdwallpaper.ui.splash;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseActivity;

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> {
    public RoundCornerProgressBar progressBar;

    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    public void onStarting() {
        this.progressBar = findViewById(R.id.progressBar);
        this.presenter.initView(this, this.progressBar);
        this.presenter.loadAds();

    }

    public void onDestroyed() {
    }

    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }
}
