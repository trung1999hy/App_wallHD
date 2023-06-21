package com.hst.offline.data.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<V extends MvpView, P extends BasePresenter<V>> extends Fragment implements MvpView, OnClickListener {
    protected BaseFragment mBaseCurrentFragment;
    private final int mDefaultValue = -1;
    protected P presenter;
    private View rootView;
//    private ViewDataBinding viewDataBinding;

    public abstract int getLayoutId();

    public abstract P initPresenter();

    public abstract void onDestroyed();

    public abstract void onStarting();


    public int getMenuId() {
        return this.mDefaultValue;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getMenuId() > this.mDefaultValue) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getMenuId() > this.mDefaultValue) {
            inflater.inflate(getMenuId(), menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId <= this.mDefaultValue) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
//        try {
//            this.viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (this.viewDataBinding == null) {
//
//        }
        this.rootView = inflater.inflate(layoutId, container, false);
//        ViewDataBinding viewDataBinding2 = this.viewDataBinding;
//        return viewDataBinding2 == null ? this.rootView : viewDataBinding2.getRoot();
        return this.rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    public View getRootView() {

        View view = this.rootView;
        if (view != null) {
            return view;
        }
        return getView();
    }

    protected static void runCurrentActivity(Activity activity, Class nextClass) {
        activity.startActivity(new Intent(activity, nextClass));
    }

    public void onDestroyView() {
        super.onDestroyView();
        P p = this.presenter;
        if (p != null) {
            p.detachLifecycle(getLifecycle());
            this.presenter.detachView();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        P p = this.presenter;
        if (p != null) {
            p.detachLifecycle(getLifecycle());
            this.presenter.detachView();
        }
        onDestroyed();
    }

    public void onClick(View view) {
    }


    public void callFragment(int layoutId, BaseFragment baseFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(layoutId, baseFragment, baseFragment.getClass().getName()).commit();
        setCurrentFragment(baseFragment);
    }


    public void setCurrentFragment(BaseFragment baseFragment) {
        this.mBaseCurrentFragment = baseFragment;
    }


    public BaseFragment getBaseCurrentFragment() {
        return this.mBaseCurrentFragment;
    }
}
