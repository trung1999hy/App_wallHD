package com.hst.offline.ui.main;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.hst.offline.App;
import com.hst.offline.R;
import com.hst.offline.data.base.BaseActivity;
import com.hst.offline.ui.main.category.CategoryFragment;
import com.hst.offline.ui.main.favourite.FavouriteFragment;
import com.hst.offline.ui.main.recent.RecentFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.hst.offline.ui.purchase.PurchaseInAppActivity;
import com.luseen.spacenavigation.SpaceNavigationView;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    public AppBarLayout appbar;
    public SpaceNavigationView bottomNavigation;
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

        this.pointLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PurchaseInAppActivity.class));
            }
        });

        this.presenter.initView(this, this.bottomNavigation);
        this.toolbar.setTitle("");
        this.toolbarTitles.setText(String.format("%s %s %s", getString(R.string.toolbar_text_unicode), getString(R.string.toolbar_title), getString(R.string.toolbar_text_unicode)));
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon(R.drawable.icon_toolbar);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.presenter.openDrawer();
            }
        });
        callFragment(R.id.fragment_container, new RecentFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        pointWallet.setText(App.getInstance().getValueCoin() +"");
    }

    public void onDestroyed() {
    }

    public void onBackPressed() {
        if (!this.presenter.checkDrawer()) {
            this.presenter.exitDialog();
        }
    }

    public void onCentreButtonClick() {
        callFragment(R.id.fragment_container, new RecentFragment());
    }

    public void onItemClick(int itemIndex, String itemName) {
        if (itemIndex == 0) {
            callFragment(R.id.fragment_container, new CategoryFragment());
        } else if (itemIndex == 1) {
            callFragment(R.id.fragment_container, new FavouriteFragment());
        }
    }

    public void menuHome() {
        callFragment(R.id.fragment_container, new RecentFragment());
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
