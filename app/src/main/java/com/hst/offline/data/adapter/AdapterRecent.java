package com.hst.offline.data.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.hst.offline.App;
import com.hst.offline.R;
import com.hst.offline.data.model.ModelWallpaperAll;
import com.hst.offline.data.utils.FavouriteDatabase;
import com.hst.offline.data.utils.Helpers;
import com.hst.offline.data.utils.Variable;
import com.hst.offline.ui.detail.DetailActivity;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class AdapterRecent extends Adapter<ViewHolder> {
    public static final int view_item = 0;
    public static final int view_native = 1;

    public ArrayList<ModelWallpaperAll> arrayList;

    public Activity context;

    public FavouriteDatabase favouriteDatabase;

    static class NativeExpressAdViewHolder extends ViewHolder {
        RelativeLayout rootLayout;

        NativeExpressAdViewHolder(View view) {
            super(view);
            this.rootLayout = view.findViewById(R.id.rootLayout);
        }
    }

    static class OriginalViewHolder extends ViewHolder {
        CardView card_gif;
        ImageView imageView;
        LikeButton likeButton;
        LottieAnimationView progressBar;

        LinearLayout point;

        OriginalViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.progressBar = view.findViewById(R.id.progressBar);
            this.likeButton = view.findViewById(R.id.likeButton);
            this.card_gif = view.findViewById(R.id.card_gif);
            this.point = view.findViewById(R.id.pointView);
        }
    }

    public AdapterRecent(Activity context2, ArrayList<ModelWallpaperAll> arrayList2) {
        this.context = context2;
        this.arrayList = arrayList2;
        this.favouriteDatabase = new FavouriteDatabase(context2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OriginalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_wallpaper, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder_parent, @SuppressLint("RecyclerView") final int position) {
        final ModelWallpaperAll modelWallpaperAll = this.arrayList.get(position);
        if (!modelWallpaperAll.wallpaper_image.equals("native")) {
            final OriginalViewHolder holder = (OriginalViewHolder) holder_parent;
            holder.card_gif.setVisibility(View.GONE);
            if (Helpers.getExt(modelWallpaperAll.wallpaper_image).equals("gif")) {
                holder.card_gif.setVisibility(View.VISIBLE);
            }
            if (modelWallpaperAll.wallpaper_folder.equals("wallpaper/Cars")) {
                holder.point.setVisibility(View.VISIBLE);
            }
            else {
                holder.point.setVisibility(View.GONE);
            }
            RequestManager with = Glide.with(this.context);
            String sb = Variable.assets_folder + modelWallpaperAll.wallpaper_image;
            with.load(sb).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.25f).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.imageView);
            holder.likeButton.setLiked(this.favouriteDatabase.isFavourite(modelWallpaperAll));
            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                public void liked(LikeButton likeButton) {
                    AdapterRecent.this.favouriteDatabase.AddtoFavorite(modelWallpaperAll);
                }

                public void unLiked(LikeButton likeButton) {
                    AdapterRecent.this.favouriteDatabase.RemoveFav(modelWallpaperAll);
                }
            });
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (modelWallpaperAll.wallpaper_folder.equals("wallpaper/Cars")) {
                        if (App.getInstance().getValueCoin() >= 2) {
                            App.getInstance().setValueCoin(App.getInstance().getValueCoin() - 2);
                        }
                        else {
                            Toast.makeText(context, "You need more coin to using this image!", Toast.LENGTH_LONG).show();
                            return ;
                        }
                    }
                    Variable.arrayListDetail.clear();
                    Variable.arrayListDetail.addAll(AdapterRecent.this.arrayList);
                    Intent intent = new Intent(AdapterRecent.this.context, DetailActivity.class);
                    intent.putExtra("position", position);
                    AdapterRecent.this.context.startActivity(intent);
                    AdapterRecent.this.context.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
                }
            });
            return;
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }

    public int getItemViewType(int position) {
        if (!this.arrayList.get(position).wallpaper_image.equals("native")) {
            return 0;
        }
        return 1;
    }
}
