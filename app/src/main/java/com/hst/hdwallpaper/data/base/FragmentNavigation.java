package com.hst.hdwallpaper.data.base;

public interface FragmentNavigation {

    public interface Presenter {
        void addFragment(BaseFragment baseFragment);
    }

    public interface View {
        void attachPresenter(Presenter presenter);
    }
}
