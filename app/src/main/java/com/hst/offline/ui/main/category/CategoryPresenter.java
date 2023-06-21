package com.hst.offline.ui.main.category;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hst.offline.R;
import com.hst.offline.Settings;
import com.hst.offline.data.adapter.AdapterCategory;
import com.hst.offline.data.base.BasePresenter;
import com.hst.offline.data.model.ModelWallpaperCategory;
import com.hst.offline.data.utils.PopulateWallpaper;
import com.hst.offline.data.utils.ProgressLoader;
import com.hst.offline.data.widgets.RecyclerViewDecoration;
import com.gun0912.tedpermission.TedPermissionBase;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class CategoryPresenter extends BasePresenter<CategoryView> {
    static final  boolean $assertionsDisabled = false;
    private Activity activity;
    private ArrayList<ModelWallpaperCategory> arrayListCategory;
    private ProgressLoader progressLoader;
    private RecyclerView recyclerView;


    public void initView(Activity activity2, View rootView) {
        this.activity = activity2;
        this.progressLoader = new ProgressLoader(activity2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progressLoader.show();
        this.arrayListCategory = new ArrayList<>();
        populateArrayList();
    }

    private void populateArrayList() {
        Activity activity2 = this.activity;
        String str = Settings.wallpaper_folder;
        for (String value : Objects.requireNonNull(PopulateWallpaper.getList(activity2, str))) {
            if (value.equals("Anime") || value.equals("Gif")) {
                continue;
            }
            Activity activity3 = this.activity;
            String sb = str +
                    File.separator +
                    value;
            String[] sub_wallpaper = PopulateWallpaper.getList(activity3, sb);
            ArrayList<ModelWallpaperCategory> arrayList = this.arrayListCategory;
            String sb2 = str +
                    File.separator +
                    value +
                    File.separator +
                    (sub_wallpaper != null ? sub_wallpaper[0] : null);
            arrayList.add(new ModelWallpaperCategory(sb2, value));
        }
        for (int i = 0; i < this.arrayListCategory.size(); i++) {
            if (i % 4 == 0 && i != 0) {
                String str2 = "banner";
                this.arrayListCategory.add(i, new ModelWallpaperCategory(str2, str2));
            }
        }
        initData();
    }

    private void initData() {
        this.recyclerView.addItemDecoration(new RecyclerViewDecoration(this.activity));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));
        AdapterCategory adapterCategory = new AdapterCategory(this.activity, this.arrayListCategory);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapterCategory);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(TedPermissionBase.REQ_CODE_REQUEST_SETTING);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        this.recyclerView.setAdapter(adapterCategory);
        this.progressLoader.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
