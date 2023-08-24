package com.hst.hdwallpaper.ui.main;

import com.hst.hdwallpaper.data.base.MvpView;

public interface MainView extends MvpView {
    void menuCategories();

    void menuFavourite();

    void menuHome();

    void inApp();
    void onCentreButtonClick();

    void onItemClick(int i, String str);
}
