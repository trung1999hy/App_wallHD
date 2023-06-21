package com.hst.offline.ui.zoom;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.hst.offline.R;
import com.hst.offline.data.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomActivity extends BaseActivity<ZoomView, ZoomPresenter> {
    public ImageView imageBack;
    public PhotoView zoomImage;

    public int getLayoutId() {
        return R.layout.activity_zoom;
    }

    public void onStarting() {
        this.imageBack = findViewById(R.id.image_back);
        this.zoomImage = findViewById(R.id.zoomImage);
        (this.presenter).initView(this, this.zoomImage, getIntent().getStringExtra("str_image"));
        this.imageBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ZoomActivity.this.finish();
            }
        });
    }


    public void onDestroyed() {
    }

    public ZoomPresenter initPresenter() {
        return new ZoomPresenter();
    }
}
