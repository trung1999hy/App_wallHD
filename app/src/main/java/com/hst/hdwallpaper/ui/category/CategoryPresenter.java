package com.hst.hdwallpaper.ui.category;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.Setting;
import com.hst.hdwallpaper.data.adapter.AdapterCategory;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.hst.hdwallpaper.data.model.WallpaperCategoryModel;
import com.hst.hdwallpaper.ui.utils.IPopulateWallpaper;
import com.hst.hdwallpaper.ui.utils.Progress;
import com.hst.hdwallpaper.ui.widgets.RecyclerViewDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CategoryPresenter extends BasePresenter<CategoryView> {
    static final boolean $assertionsDisabled = false;
    private Activity activity;
    private ArrayList<WallpaperCategoryModel> arrayListCategory;
    private Progress progress;
    private RecyclerView recyclerView;


    public void initView(Activity activity2, View rootView) {
        this.activity = activity2;
        this.progress = new Progress(activity2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progress.show();
        this.arrayListCategory = new ArrayList<>();
        populateArrayList();
    }

    private void populateArrayList() {
        Activity activity2 = this.activity;
        String str = Setting.wallpaper_folder;
        for (String value : Objects.requireNonNull(IPopulateWallpaper.getList(activity2, str))) {
            if (value.equals("Anime") || value.equals("Gif")) {
                continue;
            }
            Activity activity3 = this.activity;
            String sb = str +
                    File.separator +
                    value;
            String[] sub_wallpaper = IPopulateWallpaper.getList(activity3, sb);
            ArrayList<WallpaperCategoryModel> arrayList = this.arrayListCategory;
            String sb2 = str +
                    File.separator +
                    value +
                    File.separator +
                    (sub_wallpaper != null ? sub_wallpaper[0] : null);
            arrayList.add(new WallpaperCategoryModel(sb2, value));
        }
        for (int i = 0; i < this.arrayListCategory.size(); i++) {
            if (i % 4 == 0 && i != 0) {
                String str2 = "banner";
                this.arrayListCategory.add(i, new WallpaperCategoryModel(str2, str2));
            }
        }
        initData();
    }

    private void initData() {
        this.recyclerView.addItemDecoration(new RecyclerViewDecoration(this.activity));
        GridLayoutManager layoutManager = new GridLayoutManager(this.activity, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 0) {
                    return 2;
                } else {
                    return 1;
                }

//                return (3 - position % 3);
            }
        });
        this.recyclerView.setLayoutManager(layoutManager);
        AdapterCategory adapterCategory = new AdapterCategory(this.activity, this.arrayListCategory);
        this.recyclerView.setAdapter(adapterCategory);
        this.progress.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
