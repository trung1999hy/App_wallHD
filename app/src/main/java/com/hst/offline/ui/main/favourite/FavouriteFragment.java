package com.hst.offline.ui.main.favourite;

import com.hst.offline.R;
import com.hst.offline.data.base.BaseFragment;

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
