package com.hst.hdwallpaper.data.base;

import androidx.lifecycle.ViewModel;

public class BaseViewModel<V extends MvpView, P extends Presenter<V>> extends ViewModel {
    private P presenter;

    public void setPresenter(P presenter2) {
        if (this.presenter == null) {
            this.presenter = presenter2;
        }
    }

    public P getPresenter() {
        return this.presenter;
    }

    public void onCleared() {
        super.onCleared();
        this.presenter.onPresenterDestroy();
        this.presenter = null;
    }
}
