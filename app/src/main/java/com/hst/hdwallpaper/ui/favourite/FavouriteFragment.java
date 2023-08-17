package com.hst.hdwallpaper.ui.favourite;

import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseFragment;

public class FavouriteFragment extends BaseFragment<FavouriteView, FavouritePresenter> {
    
    public void onStarting() {
        this.presenter.initView(requireActivity(), getRootView());
    }

    
    public void onDestroyed() {
    }

    
    public int getLayoutId() {
        return R.layout.all_fragment;
    }

    
    public FavouritePresenter initPresenter() {
        return new FavouritePresenter();
    }
}
