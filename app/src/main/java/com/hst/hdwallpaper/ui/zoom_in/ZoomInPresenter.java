package com.hst.hdwallpaper.ui.zoom_in;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.hst.hdwallpaper.ui.utils.ILogger;
import com.hst.hdwallpaper.ui.utils.Progress;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomInPresenter extends BasePresenter<ZoomInView> {
    private Progress progress;


    public void initView(Context context, PhotoView zoomImage, String str_image) {
        this.progress = new Progress(context);
        this.progress.show();
        ILogger.log(str_image);
        Glide.with(context).load(str_image).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(zoomImage);
        this.progress.stopLoader();
    }
}
