package com.hst.hdwallpaper.data.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.model.WallpaperCategoryModel;
import com.hst.hdwallpaper.ui.utils.Value;
import com.hst.hdwallpaper.ui.category_list.CategoryListActivity;

import java.util.ArrayList;

public class AdapterCategory extends Adapter<ViewHolder> {
    public static final String sending_folder = "sending_folder";
    private static final int view_banner = 1;
    private static final int view_item = 0;

    public Activity activity;
    private final ArrayList<WallpaperCategoryModel> arrayList;


    class BannerAdViewHolder extends ViewHolder {
        FrameLayout bannerHolder;

        BannerAdViewHolder(View view) {
            super(view);
            this.bannerHolder = view.findViewById(R.id.admob_banner);
            showAd(this.bannerHolder);
        }
        private void showAd(FrameLayout bannerHolder) {
            //AdmobAds.loadBanner(AdapterCategory.this.activity, bannerHolder);
        }
    }

    static class OriginalViewHolder extends ViewHolder {
        ImageView imageView;
        LottieAnimationView progressBar;
        TextView title;

        OriginalViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.progressBar = view.findViewById(R.id.progressBar);
            this.title = view.findViewById(R.id.title);
        }
    }

    public AdapterCategory(Activity activity2, ArrayList<WallpaperCategoryModel> arrayList2) {
        this.activity = activity2;
        this.arrayList = arrayList2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new OriginalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_category, parent, false));
        }
        return new BannerAdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_banner_content, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder_parent, int position) {
        final WallpaperCategoryModel wallpaperCategoryModel = this.arrayList.get(position);
        if (!wallpaperCategoryModel.category_image.equals("banner")) {
            final OriginalViewHolder holder = (OriginalViewHolder) holder_parent;
            RequestManager with = Glide.with(this.activity);
            String sb = Value.assets_folder + wallpaperCategoryModel.category_image;
            (with.load(sb).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .thumbnail(0.25f)
                    .listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.imageView);
            holder.title.setText(wallpaperCategoryModel.category_folder);
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(AdapterCategory.this.activity, CategoryListActivity.class);
                    intent.putExtra(AdapterCategory.sending_folder, wallpaperCategoryModel.category_folder);
                    AdapterCategory.this.activity.startActivity(intent);
                    AdapterCategory.this.activity.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                }
            });
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }

    public int getItemViewType(int position) {
        if (!this.arrayList.get(position).category_image.equals("banner")) {
            return 0;
        }
        return 1;
    }
}
