package com.hst.offline.ui.main.recent;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
import com.hst.offline.R;
import com.hst.offline.Settings;
import com.hst.offline.data.adapter.AdapterRecent;
import com.hst.offline.data.base.BasePresenter;
import com.hst.offline.data.model.ModelWallpaperAll;
import com.hst.offline.data.utils.PopulateWallpaper;
import com.hst.offline.data.utils.ProgressLoader;
import com.hst.offline.data.utils.Variable;
import com.hst.offline.data.widgets.RecyclerViewDecoration;
import com.gun0912.tedpermission.TedPermissionBase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class RecentPresenter extends BasePresenter<RecentView> {

    public ArrayList<ModelWallpaperAll> arrayListAll = new ArrayList<>();
    private Activity context;
    private ProgressLoader progressLoader;
    private RecyclerView recyclerView;


    public void initView(Activity context2, View rootView) {
        this.context = context2;

        this.progressLoader = new ProgressLoader(context2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progressLoader.show();
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
        Variable.native_loaded = false;
        RecentPresenter.this.populateArrayList();
    }


    public void populateArrayList() {
        String wallpaperFolder = Settings.wallpaper_folder;
        for (String value : Objects.requireNonNull(PopulateWallpaper.getList(this.context, wallpaperFolder))) {
            String sb = wallpaperFolder + File.separator + value;
            String[] sub_wallpaper = PopulateWallpaper.getList(this.context, sb);
            if (sub_wallpaper != null) {
                for (String s : sub_wallpaper) {
                    String wallpaper_image = wallpaperFolder + File.separator + value + File.separator + s;
                    ArrayList<ModelWallpaperAll> arrayList = this.arrayListAll;
                    String sb3 = wallpaperFolder + File.separator + value;
                    arrayList.add(new ModelWallpaperAll(wallpaper_image, sb3));
                }
            }
        }
        Collections.shuffle(this.arrayListAll);
        if (Variable.native_loaded) {
            for (int i = 0; i < this.arrayListAll.size(); i++) {
                if (i % (Settings.native_wallpaper_position + 1) == 0 && i != 0) {
                    String str2 = "native";
                    this.arrayListAll.add(i - 1, new ModelWallpaperAll(str2, str2));
                }
            }
        }
        initData();
    }

    private void initData() {
        this.recyclerView.addItemDecoration(new RecyclerViewDecoration(this.context));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context, 3);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int position) {
                if (RecentPresenter.this.arrayListAll.get(position).wallpaper_image.equals("native")) {
                    return 3;
                }
                return 1;
            }
        });
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(new AdapterRecent(this.context, this.arrayListAll));
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(TedPermissionBase.REQ_CODE_REQUEST_SETTING);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        this.recyclerView.setAdapter(scaleInAnimationAdapter);
        this.progressLoader.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
