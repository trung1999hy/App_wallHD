package com.hst.offline.ui.zoom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.hst.offline.data.base.BasePresenter;
import com.hst.offline.data.utils.Logger;
import com.hst.offline.data.utils.ProgressLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomPresenter extends BasePresenter<ZoomView> {
    private ProgressLoader progressLoader;


    public void initView(Context context, PhotoView zoomImage, String str_image) {
        this.progressLoader = new ProgressLoader(context);
        this.progressLoader.show();
        Logger.log(str_image);
        Glide.with(context).load(str_image).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(zoomImage);
        this.progressLoader.stopLoader();
    }
}
