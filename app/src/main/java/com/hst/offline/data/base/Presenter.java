package com.hst.offline.data.base;

import androidx.lifecycle.Lifecycle;

public interface Presenter<V extends MvpView> {
    void attachLifecycle(Lifecycle lifecycle);

    void attachView(V v);

    void detachLifecycle(Lifecycle lifecycle);

    void detachView();

    void onPresenterCreated();

    void onPresenterDestroy();
}
