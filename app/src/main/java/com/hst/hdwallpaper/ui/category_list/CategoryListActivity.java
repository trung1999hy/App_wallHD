package com.hst.hdwallpaper.ui.category_list;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.adapter.AdapterCategory;
import com.hst.hdwallpaper.data.base.BaseActivity;

public class CategoryListActivity extends BaseActivity<CategoryListView, CategoryListPresenter> {
    public ImageView imageBack;
    public RecyclerView recyclerView;

    public int getLayoutId() {
        return R.layout.activity_category_list;
    }


    public void onStarting() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.imageBack = findViewById(R.id.image_back);
        this.presenter.initView(this, this.recyclerView, getIntent().getStringExtra(AdapterCategory.sending_folder));
        this.imageBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CategoryListActivity.this.finish();
            }
        });
    }


    public void onDestroyed() {
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }


    public CategoryListPresenter initPresenter() {
        return new CategoryListPresenter();
    }
}
