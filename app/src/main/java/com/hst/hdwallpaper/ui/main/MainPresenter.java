package com.hst.hdwallpaper.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;

import java.util.Objects;

public class MainPresenter extends BasePresenter<MainView> {

    public Activity activity;
    public SlidingRootNav slidingRootNav;
    public SpaceNavigationView spaceNavigationView;


    public void initView(Activity activity2, SpaceNavigationView spaceNavigationView2) {
        this.activity = activity2;
        this.spaceNavigationView = spaceNavigationView2;

        bottomNavigation();

    }

    private void bottomNavigation() {
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.icon_home));
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.icon_categories));
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.icon_favourite));
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.dollar));
        this.spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        this.spaceNavigationView.showIconOnly();
        this.spaceNavigationView.changeCurrentItem(-1);
        this.spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            public void onCentreButtonClick() {

            }

            public void onItemClick(int itemIndex, String itemName) {
                try {
                    MainPresenter.this.getMvpView().onItemClick(itemIndex, itemName);
                }catch (Throwable throwable){
//
                }
            }

            public void onItemReselected(int itemIndex, String itemName) {
                MainPresenter.this.getMvpView().onItemClick(itemIndex, itemName);
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
