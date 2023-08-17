package com.hst.hdwallpaper.data.base;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;

public abstract class BasePresenter<V extends MvpView> implements LifecycleObserver, Presenter<V> {
    private V mMvpView;
    private Bundle stateBundle;

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }

    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    public void detachView() {
        this.mMvpView = null;
    }

    public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    public boolean isViewAttached() {
        return this.mMvpView != null;
    }

    public V getMvpView() {
        return this.mMvpView;
    }

    public Bundle getStateBundle() {
        Bundle bundle = this.stateBundle;
        if (bundle != null) {
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        this.stateBundle = bundle2;
        return bundle2;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public void onPresenterDestroy() {
        Bundle bundle = this.stateBundle;
        if (bundle != null && !bundle.isEmpty()) {
            this.stateBundle.clear();
        }
    }

    public void onPresenterCreated() {
    }
}
