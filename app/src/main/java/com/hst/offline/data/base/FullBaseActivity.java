package com.hst.offline.data.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public abstract class FullBaseActivity<V extends MvpView, P extends BasePresenter<V>> extends AppCompatActivity implements MvpView {
    protected P presenter;

    public abstract int getLayoutId();

    public abstract P initPresenter();

    public abstract void onDestroyed();

    public abstract void onStarting();

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        Log.d("CODERTUBE", "@@@@ onCreate 1");

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

    public void onDestroy() {
        onDestroyed();
        super.onDestroy();
        this.presenter.detachLifecycle(getLifecycle());
        this.presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
