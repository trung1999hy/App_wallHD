package com.hst.hdwallpaper.ui.favourite;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hst.hdwallpaper.data.adapter.AdapterRecent;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.hst.hdwallpaper.data.model.WallpaperAllModel;
import com.hst.hdwallpaper.ui.utils.DatabaseFavourite;
import com.hst.hdwallpaper.ui.utils.Progress;
import com.hst.hdwallpaper.ui.widgets.RecyclerViewDecoration;

import com.hst.hdwallpaper.R;

import java.util.ArrayList;

public class FavouritePresenter extends BasePresenter<FavouriteView> {
    private ArrayList<WallpaperAllModel> arrayListAll;
    private Activity context;
    private Progress progress;
    private RecyclerView recyclerView;
    private AdapterRecent adapterRecent;


    public void initView(Activity context2, View rootView) {
        this.context = context2;
        DatabaseFavourite DatabaseFavourite = new DatabaseFavourite(context2);
        this.progress = new Progress(context2);
        this.recyclerView = rootView.findViewById(R.id.recyclerView);
        this.recyclerView.setVisibility(View.GONE);
        this.progress.show();
        this.arrayListAll = DatabaseFavourite.getAllData();
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
