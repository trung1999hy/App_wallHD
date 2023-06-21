package com.hst.offline.ui.listcategory;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.hst.offline.R;
import com.hst.offline.data.adapter.AdapterCategory;
import com.hst.offline.data.base.BaseActivity;

public class ListCategoryActivity extends BaseActivity<ListCategoryView, ListCategoryPresenter> {
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
                ListCategoryActivity.this.finish();
            }
        });
    }


    public void onDestroyed() {
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }


    public ListCategoryPresenter initPresenter() {
        return new ListCategoryPresenter();
    }
}
