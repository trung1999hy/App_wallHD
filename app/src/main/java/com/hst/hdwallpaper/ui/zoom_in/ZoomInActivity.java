package com.hst.hdwallpaper.ui.zoom_in;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseActivity;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomInActivity extends BaseActivity<ZoomInView, ZoomInPresenter> {
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
                ZoomInActivity.this.finish();
            }
        });
    }


    public void onDestroyed() {
    }

    public ZoomInPresenter initPresenter() {
        return new ZoomInPresenter();
    }
}
