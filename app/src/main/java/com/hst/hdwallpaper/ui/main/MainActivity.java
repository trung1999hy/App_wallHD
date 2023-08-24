package com.hst.hdwallpaper.ui.main;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hst.hdwallpaper.App;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseActivity;
import com.hst.hdwallpaper.ui.category.CategoryFragment;
import com.hst.hdwallpaper.ui.favourite.FavouriteFragment;
import com.hst.hdwallpaper.ui.purchase.PurchaseInAppActivity;
import com.hst.hdwallpaper.ui.recent.RecentFragment;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    public AppBarLayout appbar;
    public BottomNavigationView bottomNavigation;
    public FrameLayout fragmentContainer;
    public Toolbar toolbar;
    public TextView toolbarTitles;

    public LinearLayout pointLayout;
    public TextView pointWallet;

    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void onStarting() {
        this.appbar = findViewById(R.id.appbar);
        this.bottomNavigation = findViewById(R.id.bottom_navigation);
        this.fragmentContainer = findViewById(R.id.fragment_container);
        this.toolbar = findViewById(R.id.toolbar);
        this.toolbarTitles = findViewById(R.id.toolbar_titles);
        this.pointLayout = findViewById(R.id.pointView);
        this.pointWallet = findViewById(R.id.pointWallet);
        this.presenter.initView(this, this.bottomNavigation);
        this.toolbar.setTitle("");
        this.toolbarTitles.setText(getString(R.string.toolbar_title));
        setSupportActionBar(this.toolbar);
        pointLayout.setVisibility(View.GONE);
        callFragment(R.id.fragment_container, new RecentFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointWallet.setText(App.getInstance().getValueCoin() + "");
    }

    public void onDestroyed() {
    }

    public void onBackPressed() {
        this.presenter.exitDialog();
    }

    public void onCentreButtonClick() {

    }

    public void onItemClick(int itemIndex) {
        if (itemIndex == R.id.home) {
            pointLayout.setVisibility(View.GONE);
            callFragment(R.id.fragment_container, new RecentFragment());
        } else if (itemIndex == R.id.categories) {
            pointLayout.setVisibility(View.GONE);
            callFragment(R.id.fragment_container, new CategoryFragment());
        } else if (itemIndex == R.id.favourite) {
            pointLayout.setVisibility(View.GONE);
            callFragment(R.id.fragment_container, new FavouriteFragment());
        } else if (itemIndex == R.id.coin) {
            pointLayout.setVisibility(View.VISIBLE);
            inApp();
        }
    }

    public void menuHome() {
        callFragment(R.id.fragment_container, new RecentFragment());
    }

    @Override
    public void inApp() {
        callFragment(R.id.fragment_container, new PurchaseInAppActivity());
    }

    public void menuCategories() {
        callFragment(R.id.fragment_container, new CategoryFragment());
    }

    public void menuFavourite() {
        callFragment(R.id.fragment_container, new FavouriteFragment());
    }


    public MainPresenter initPresenter() {
        return new MainPresenter();
    }
}
