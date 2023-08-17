package com.hst.hdwallpaper.ui.category_list;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class CategoryListPresenter extends BasePresenter<CategoryListView> {

    public ArrayList<WallpaperAllModel> arrayListAll;
    private Activity context;
    private String folder;
    private Progress progress;
    private RecyclerView recyclerView;
    private AdapterRecent adapterRecent;


    public void initView(Activity context2, RecyclerView recyclerView2, String folder2) {
        this.context = context2;
        this.recyclerView = recyclerView2;
        this.folder = folder2;
        this.progress = new Progress(context2);
        this.progress.show();
        recyclerView2.setVisibility(View.GONE);
        this.arrayListAll = new ArrayList<>();
        populateArrayList();
    }

    private void populateArrayList() {
        Activity activity = this.context;
        StringBuilder sb = new StringBuilder();
        String str = Setting.wallpaper_folder;
        sb.append(str);
        sb.append(File.separator);
        sb.append(this.folder);
        for (String s : Objects.requireNonNull(IPopulateWallpaper.getList(activity, sb.toString()))) {
            String wallpaper_image = str + File.separator + this.folder + File.separator + s;
            ArrayList<WallpaperAllModel> arrayList = this.arrayListAll;
            String sb3 = str + File.separator + this.folder;
            arrayList.add(new WallpaperAllModel(wallpaper_image, sb3));
        }
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
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
        adapterRecent = new AdapterRecent(this.context, this.arrayListAll);
        this.recyclerView.setAdapter(adapterRecent);
        this.progress.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
