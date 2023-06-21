package com.hst.offline.ui.main.favourite;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hst.offline.R;
import com.hst.offline.data.adapter.AdapterRecent;
import com.hst.offline.data.base.BasePresenter;
import com.hst.offline.data.model.ModelWallpaperAll;
import com.hst.offline.data.utils.FavouriteDatabase;
import com.hst.offline.data.utils.ProgressLoader;
import com.hst.offline.data.widgets.RecyclerViewDecoration;
import com.gun0912.tedpermission.TedPermissionBase;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import java.util.ArrayList;

public class FavouritePresenter extends BasePresenter<FavouriteView> {
    private ArrayList<ModelWallpaperAll> arrayListAll;
    private Activity context;
    private ProgressLoader progressLoader;
    private RecyclerView recyclerView;

    
    public void initView(Activity context2, View rootView) {
        this.context = context2;
        FavouriteDatabase favouriteDatabase = new FavouriteDatabase(context2);
        this.progressLoader = new ProgressLoader(context2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progressLoader.show();
        this.arrayListAll = favouriteDatabase.getAllData();
        initData();
    }

    private void initData() {
        this.recyclerView.addItemDecoration(new RecyclerViewDecoration(this.context));
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(new AdapterRecent(this.context, this.arrayListAll));
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(TedPermissionBase.REQ_CODE_REQUEST_SETTING);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator(0.5f));
        this.recyclerView.setAdapter(scaleInAnimationAdapter);
        this.progressLoader.stopLoader();
        this.recyclerView.setVisibility(View.VISIBLE);
    }
}
