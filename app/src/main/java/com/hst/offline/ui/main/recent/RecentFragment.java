package com.hst.offline.ui.main.recent;

import com.hst.offline.R;
import com.hst.offline.data.base.BaseFragment;

public class RecentFragment extends BaseFragment<RecentView, RecentPresenter> {
    
    public void onStarting() {
        this.presenter.initView(requireActivity(), getRootView());
    }

    
    public void onDestroyed() {
    }

    
    public int getLayoutId() {
        return R.layout.all_fragment;
    }

    
    public RecentPresenter initPresenter() {
        return new RecentPresenter();
    }
}
