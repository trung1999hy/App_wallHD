package com.hst.offline.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.hst.offline.R;
import com.hst.offline.data.base.BasePresenter;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Objects;

public class MainPresenter extends BasePresenter<MainView> {

    public Activity activity;
    public SlidingRootNav slidingRootNav;
    public SpaceNavigationView spaceNavigationView;


    public void initView(Activity activity2, SpaceNavigationView spaceNavigationView2) {
        this.activity = activity2;
        this.spaceNavigationView = spaceNavigationView2;
        initializeConsent();
        bottomNavigation();
        initDrawer();
    }

    private void bottomNavigation() {
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.icon_categories));
        this.spaceNavigationView.addSpaceItem(new SpaceItem(null, R.drawable.icon_favourite));
        this.spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        this.spaceNavigationView.showIconOnly();
        this.spaceNavigationView.changeCurrentItem(-1);
        this.spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            public void onCentreButtonClick() {
                MainPresenter.this.getMvpView().onCentreButtonClick();
            }

            public void onItemClick(int itemIndex, String itemName) {
                MainPresenter.this.getMvpView().onItemClick(itemIndex, itemName);
            }

            public void onItemReselected(int itemIndex, String itemName) {
                MainPresenter.this.getMvpView().onItemClick(itemIndex, itemName);
            }
        });
    }


    public void openDrawer() {
        this.slidingRootNav.openMenu(true);
    }


    public boolean checkDrawer() {
        if (!this.slidingRootNav.isMenuOpened()) {
            return false;
        }
        this.slidingRootNav.closeMenu(true);
        return true;
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

    private void initializeConsent() {
    }

    private void showBannerAds() {
    }

    private void initDrawer() {
        this.slidingRootNav = new SlidingRootNavBuilder(this.activity).withMenuOpened(false).withContentClickableWhenMenuOpened(false).withGravity(SlideGravity.LEFT).withMenuLayout(R.layout.drawer_layout).inject();
        final Activity _activity = MainPresenter.this.activity;

        this.slidingRootNav.getLayout().findViewById(R.id.menuHome).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                MainPresenter.this.getMvpView().menuHome();
                MainPresenter.this.spaceNavigationView.changeCurrentItem(-1);
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuCategories).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                MainPresenter.this.getMvpView().menuCategories();
                MainPresenter.this.spaceNavigationView.changeCurrentItem(0);
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuFavourite).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                MainPresenter.this.getMvpView().menuFavourite();
                MainPresenter.this.spaceNavigationView.changeCurrentItem(1);
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuShare).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                String sb = "Download " +
                        MainPresenter.this.activity.getString(R.string.app_name) +
                        " for Whatsapp in : \nhttps://play.google.com/store/apps/details?id=" +
                        _activity.getPackageName();
                sendIntent.putExtra("android.intent.extra.TEXT", sb);
                sendIntent.setType("text/plain");
                _activity.startActivity(sendIntent);
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuRate).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                String sb = "https://play.google.com/store/apps/details?id=" +
                        _activity.getPackageName();
                _activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb)));
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuMore).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                String sb = "https://play.google.com/store/apps/dev?id=" +
                        _activity.getString(R.string.play_developer_id);
                _activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb)));
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuAbout).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                MainPresenter.this.aboutDialog();
            }
        });
        this.slidingRootNav.getLayout().findViewById(R.id.menuPrivacy).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainPresenter.this.slidingRootNav.closeMenu(true);
                _activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(MainPresenter.this.activity.getString(R.string.privacy_police))));
            }
        });
    }


    public void aboutDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.activity, R.style.popupDialog);
        alert.setView(this.activity.getLayoutInflater().inflate(R.layout.dialog_about, null));
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
    }
}
