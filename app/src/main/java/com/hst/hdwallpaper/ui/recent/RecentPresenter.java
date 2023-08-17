package com.hst.hdwallpaper.ui.recent;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.Setting;
import com.hst.hdwallpaper.data.adapter.AdapterRecent;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.hst.hdwallpaper.data.model.WallpaperAllModel;
import com.hst.hdwallpaper.ui.utils.IPopulateWallpaper;
import com.hst.hdwallpaper.ui.utils.Progress;
import com.hst.hdwallpaper.ui.utils.Value;
import com.hst.hdwallpaper.ui.widgets.RecyclerViewDecoration;
import com.gun0912.tedpermission.TedPermissionBase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class RecentPresenter extends BasePresenter<RecentView> {

    public ArrayList<WallpaperAllModel> arrayListAll = new ArrayList<>();
    private Activity context;
    private Progress progress;
    private RecyclerView recyclerView;
    private AdapterRecent adapterRecent;


    public void initView(Activity context2, View rootView) {
        this.context = context2;

        this.progress = new Progress(context2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progress.show();
        this.arrayListAll = new ArrayList<>();
        loadNative();
    }

    private void loadNative() {
//        new Builder(this.context, Settings.admob_native_id)
//                .forUnifiedNativeAd(new OnUnifiedNativeAdLoadedListener() {
//                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAds) {
//
//                    }
//                })
//                .withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                        super.onAdFailedToLoad(loadAdError);
//                        Variable.native_loaded = false;
//                        RecentPresenter.this.populateArrayList();
//                    }
//                })
//                .build()
//                .loadAds(new AdRequest.Builder().build(), 1);
        Value.native_loaded = false;
        RecentPresenter.this.populateArrayList();
    }


    public void populateArrayList() {
        String wallpaperFolder = Setting.wallpaper_folder;
        for (String value : Objects.requireNonNull(IPopulateWallpaper.getList(this.context, wallpaperFolder))) {
            String sb = wallpaperFolder + File.separator + value;
            String[] sub_wallpaper = IPopulateWallpaper.getList(this.context, sb);
            if (sub_wallpaper != null) {
                for (String s : sub_wallpaper) {
                    String wallpaper_image = wallpaperFolder + File.separator + value + File.separator + s;
                    ArrayList<WallpaperAllModel> arrayList = this.arrayListAll;
                    String sb3 = wallpaperFolder + File.separator + value;
                    arrayList.add(new WallpaperAllModel(wallpaper_image, sb3));
                }
            }
        }
        Collections.shuffle(this.arrayListAll);
        if (Value.native_loaded) {
            for (int i = 0; i < this.arrayListAll.size(); i++) {
                if (i % (Setting.native_wallpaper_position + 1) == 0 && i != 0) {
                    String str2 = "native";
                    this.arrayListAll.add(i - 1, new WallpaperAllModel(str2, str2));
                }
            }
        }
        initData();
    }

    private void initData() {
        this.recyclerView.addItemDecoration(new RecyclerViewDecoration(this.context));
        this.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
        adapterRecent = new AdapterRecent(this.context, this.arrayListAll);
        this.recyclerView.setAdapter(adapterRecent);
        this.progress.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
