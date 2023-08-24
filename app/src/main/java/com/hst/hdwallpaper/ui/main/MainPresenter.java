package com.hst.hdwallpaper.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.yarolegovich.slidingrootnav.SlidingRootNav;

import java.util.Objects;

public class MainPresenter extends BasePresenter<MainView> {

    public Activity activity;
    public SlidingRootNav slidingRootNav;
    public BottomNavigationView bottomNavigationView;


    public void initView(Activity activity2, BottomNavigationView spaceNavigationView2) {
        this.activity = activity2;
        this.bottomNavigationView = spaceNavigationView2;

        bottomNavigation();

    }

    private void bottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MainPresenter.this.getMvpView().onItemClick(item.getItemId());
                return true;
            }
        });
    }


    public void exitDialog() {
        final Dialog dialog = new Dialog(this.activity, R.style.popupDialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        Button button1 = dialog.findViewById(R.id.button1);
        Button button2 = dialog.findViewById(R.id.button2);
        ((TextView) dialog.findViewById(R.id.content)).setText("Are you sure want to exit ?");
        button1.setText("NO");
        button2.setText("EXIT");
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.activity.finish();
            }
        });
        dialog.show();
    }


}
