package com.hst.offline.ui.main;

import com.hst.offline.data.base.MvpView;

public interface MainView extends MvpView {
    void menuCategories();

    void menuFavourite();

    void menuHome();

    void onCentreButtonClick();

    void onItemClick(int i, String str);
}
