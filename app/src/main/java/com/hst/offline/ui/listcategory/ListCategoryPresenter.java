package com.hst.offline.ui.listcategory;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ListCategoryPresenter extends BasePresenter<ListCategoryView> {

    public ArrayList<ModelWallpaperAll> arrayListAll;
    private Activity context;
    private String folder;
    private ProgressLoader progressLoader;
    private RecyclerView recyclerView;


    public void initView(Activity context2, RecyclerView recyclerView2, String folder2) {
        this.context = context2;
        this.recyclerView = recyclerView2;
        this.folder = folder2;
        this.progressLoader = new ProgressLoader(context2);
        this.progressLoader.show();
        recyclerView2.setVisibility(View.GONE);
        this.arrayListAll = new ArrayList<>();
        populateArrayList();
    }

    private void populateArrayList() {
        Activity activity = this.context;
        StringBuilder sb = new StringBuilder();
        String str = Settings.wallpaper_folder;
        sb.append(str);
        sb.append(File.separator);
        sb.append(this.folder);
        for (String s : Objects.requireNonNull(PopulateWallpaper.getList(activity, sb.toString()))) {
            String wallpaper_image = str + File.separator + this.folder + File.separator + s;
            ArrayList<ModelWallpaperAll> arrayList = this.arrayListAll;
            String sb3 = str + File.separator + this.folder;
            arrayList.add(new ModelWallpaperAll(wallpaper_image, sb3));
        }
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context, 2);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int position) {
                if (ListCategoryPresenter.this.arrayListAll.get(position).wallpaper_image.equals("native")) {
                    return 2;
                }
                return 1;
            }
        });
        AdapterRecent adapterRecent = new AdapterRecent(this.context, this.arrayListAll);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapterRecent);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(TedPermissionBase.REQ_CODE_REQUEST_SETTING);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        this.recyclerView.setAdapter(adapterRecent);
        this.progressLoader.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
