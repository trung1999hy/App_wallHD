package com.hst.hdwallpaper.data.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hst.hdwallpaper.ui.purchase.PurchaseInAppActivity;

public abstract class BaseActivity<V extends MvpView, P extends BasePresenter<V>> extends AppCompatActivity implements MvpView {
    protected BaseFragment mBaseCurrentFragment;
    protected P presenter;
    protected Bundle savedInstanceState;

    public abstract int getLayoutId();

    public abstract P initPresenter();

    public abstract void onDestroyed();

    public abstract void onStarting();


    public void onCreate(Bundle savedInstanceState2) {
        this.savedInstanceState = savedInstanceState2;
        super.onCreate(savedInstanceState2);

        setContentView(getLayoutId());
        BaseViewModel<V, P> viewModel = new ViewModelProvider(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isPresenterCreated = true;
        }
        this.presenter = viewModel.getPresenter();
        this.presenter.attachLifecycle(getLifecycle());
        this.presenter.attachView((V) this);
        if (isPresenterCreated) {
            this.presenter.onPresenterCreated();
        }
        onStarting();
    }

    protected static void runCurrentActivity(Activity activity, Class nextClass) {
        activity.startActivity(new Intent(activity, nextClass));
    }


    public Bundle getInstanceState() {
        return this.savedInstanceState;
    }


    public void onDestroy() {
        onDestroyed();
        super.onDestroy();
        this.presenter.detachLifecycle(getLifecycle());
        this.presenter.detachView();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void callFragment(int layoutId, BaseFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().replace(layoutId, baseFragment, baseFragment.getClass().getName()).commit();
        setCurrentFragment(baseFragment);
    }


    public void setCurrentFragment(BaseFragment baseFragment) {
        this.mBaseCurrentFragment = baseFragment;
    }


    public BaseFragment getBaseCurrentFragment() {
        return this.mBaseCurrentFragment;
    }
}
