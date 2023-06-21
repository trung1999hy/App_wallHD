package com.hst.offline.ui.main.category;

import com.hst.offline.R;
import com.hst.offline.data.base.BaseFragment;

public class CategoryFragment extends BaseFragment<CategoryView, CategoryPresenter> {
    
    public void onStarting() {
        this.presenter.initView(requireActivity(), getRootView());
    }

    
    public void onDestroyed() {
    }

    
    public int getLayoutId() {
        return R.layout.all_fragment;
    }

    
    public CategoryPresenter initPresenter() {
        return new CategoryPresenter();
    }
}
