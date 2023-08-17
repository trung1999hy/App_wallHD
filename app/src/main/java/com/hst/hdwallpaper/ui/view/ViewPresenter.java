package com.hst.hdwallpaper.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BasePresenter;
import com.hst.hdwallpaper.ui.utils.*;
import com.hst.hdwallpaper.ui.zoom_in.ZoomInActivity;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCrop.Options;
import de.mateware.snacky.Snacky;
import flipagram.assetcopylib.AssetCopier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewPresenter extends BasePresenter<View> {
    public int SET_WALLPAPER_RESULT = 101;
    public Activity activity;
    private AppCompatActivity activitys;
    public Progress progress;
    public AllAngleExpandableButton buttonExpandable;
    public ViewPager enchantedViewPager;
    public ImageView imageBack;

    class AdapterDetail extends PagerAdapter {
        private final LayoutInflater inflater;

        AdapterDetail() {
            this.inflater = ViewPresenter.this.activity.getLayoutInflater();
        }

        public int getCount() {
            return Value.arrayListDetail.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull android.view.View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return -2;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int pos) {
            android.view.View imageLayout = this.inflater.inflate(R.layout.view_pager_wallpaper, container, false);
            ImageView imageView = imageLayout.findViewById(R.id.image);
            final LottieAnimationView progressBar = imageLayout.findViewById(R.id.progressBar);
            RequestManager with = Glide.with(ViewPresenter.this.activity);
            String sb = Value.assets_folder + Value.arrayListDetail.get(pos).wallpaper_image;
            with.load(sb).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(android.view.View.GONE);
                    return false;
                }
            }).into(imageView);
            container.addView(imageLayout);
            return imageLayout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((android.view.View) object);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class SetWallpaperTask extends AsyncTask<String, String, String> {
        Bitmap bmImg;
        String options;

        private SetWallpaperTask(Bitmap bmImg2, String options2) {
            this.bmImg = bmImg2;
            this.options = options2;
        }


        public void onPreExecute() {
            super.onPreExecute();
        }


        public String doInBackground(String... args) {
            return null;
        }

        public void onPostExecute(java.lang.String r9) {
            WallpaperManager wpm = WallpaperManager.getInstance(ViewPresenter.this.activity.getApplicationContext());
            try {
                wpm.setWallpaperOffsetSteps(0.0f, 0.0f);
                if (Build.VERSION.SDK_INT >= 24) {
                    String str = this.options;
                    int hashCode = str.hashCode();
                    if (hashCode != 2044801) {
                        if (hashCode != 2223327) {
                            if (hashCode == 2342187 && str.equals("LOCK")) {
                                wpm.setBitmap(this.bmImg, null, true, WallpaperManager.FLAG_LOCK);
                            }
                        } else if (str.equals("HOME")) {
                            wpm.setBitmap(this.bmImg, null, true, WallpaperManager.FLAG_SYSTEM);
                        }
                    } else if (str.equals("BOTH")) {
                        wpm.setBitmap(this.bmImg);
                    }
                } else {
                    wpm.setBitmap(this.bmImg);
                }
                Snacky.builder().setActivity(ViewPresenter.this.activity).setText(ViewPresenter.this.activity.getString(R.string.wallpaper_set_success)).setDuration(Snacky.LENGTH_LONG).success().show();
            } catch (Exception e) {
                e.printStackTrace();
                Snacky.builder().setActivity(ViewPresenter.this.activity).setText(ViewPresenter.this.activity.getString(R.string.wallpaper_set_error)).setDuration(Snacky.LENGTH_LONG).error().show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class ShareTask extends AsyncTask<Boolean, Boolean, Boolean> {
        private final Bitmap bitmap;

        public File saved_folder;

        private ShareTask(Bitmap bitmap2) {
            this.bitmap = bitmap2;
        }


        public void onPreExecute() {
            ViewPresenter.this.progress.show();
            super.onPreExecute();
        }


        public Boolean doInBackground(Boolean... args) {
            try {
                Bitmap map = Bitmap.createBitmap(this.bitmap);
                File cachePath = new File(ViewPresenter.this.activity.getExternalCacheDir(), "shared_folder");
                if (!cachePath.exists()) {
                    cachePath.mkdirs();
                }
                String sb = cachePath + File.separator + "share_image.png";
                this.saved_folder = new File(sb);
                FileOutputStream stream = new FileOutputStream(this.saved_folder);
                map.compress(CompressFormat.PNG, 100, stream);
                stream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }


        public void onPostExecute(Boolean args) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ViewPresenter.this.progress.dismiss();
                    try {
                        StrictMode.setVmPolicy(new Builder().build());
                        Intent share = new Intent("android.intent.action.SEND");
                        share.setType("image/*");
                        String sb = "file://" +
                                ShareTask.this.saved_folder.getAbsolutePath();
                        share.putExtra("android.intent.extra.STREAM", Uri.parse(sb));
                        String sb2 = ViewPresenter.this.activity.getString(R.string.get_more_wall) +
                                "\n" +
                                ViewPresenter.this.activity.getString(R.string.app_name) +
                                " - https://play.google.com/store/apps/details?id=" +
                                ViewPresenter.this.activity.getPackageName();
                        share.putExtra("android.intent.extra.TEXT", sb2);
                        ViewPresenter.this.activity.startActivity(Intent.createChooser(share, "Share Wallpaper"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        }
    }

    public void initView(AppCompatActivity activitys2, Activity activity2,
                         AllAngleExpandableButton buttonExpandable,
                         ViewPager enchantedViewPager,
                         ImageView imageBack, int position) {
        this.activity = activity2;
        this.activitys = activitys2;
        this.enchantedViewPager = enchantedViewPager;
        this.buttonExpandable = buttonExpandable;
        this.imageBack = imageBack;


        this.enchantedViewPager.setVisibility(android.view.View.GONE);
        this.enchantedViewPager.setAdapter(new AdapterDetail());
        this.enchantedViewPager.setCurrentItem(position);
        this.progress = new Progress(activity2);
        this.progress.show();
        checkPermission();
        initMenu();
        this.progress.stopLoader();
        this.enchantedViewPager.setVisibility(android.view.View.VISIBLE);
    }

    private void initMenu() {
        this.imageBack.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View view) {
                ViewPresenter.this.getMvpView().onImageBack();
            }
        });
        List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.ic_magic, R.drawable.ic_zoom, R.drawable.icon_download, R.drawable.ic_crop};
        int[] color = {R.color.colorButtonDetail, R.color.colorButtonDetail, R.color.colorButtonDetail, R.color.colorButtonDetail, R.color.colorButtonDetail};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this.activity, drawable[i], 11.0f);
            buttonData.setBackgroundColorId(this.activity, color[i]);
            buttonDatas.add(buttonData);
        }
        this.buttonExpandable.setButtonDatas(buttonDatas);
        this.buttonExpandable.setButtonEventListener(new ButtonEventListener() {
            public void onButtonClicked(int index) {
                ILogger.log(index);
                if (index != 1) {
                    if (index != 2) {
                        if (index != 3) {
                            if (index == 4 && ViewPresenter.this.checkPermission()) {
                                ViewPresenter viewPresenter = ViewPresenter.this;
                                viewPresenter.shareImage(viewPresenter.enchantedViewPager.getCurrentItem());
                            }
                        } else if (!ViewPresenter.this.checkPermission()) {
                        } else {
                            if (Objects.requireNonNull(IHelpers.getExt(Value.arrayListDetail.get(ViewPresenter.this.enchantedViewPager.getCurrentItem()).wallpaper_image)).equals("gif")) {
                                ViewPresenter viewPresenter2 = ViewPresenter.this;
                                viewPresenter2.setWallpaperGIF(viewPresenter2.enchantedViewPager.getCurrentItem());
                                return;
                            }
                            ViewPresenter.this.cropWallpaper();
                        }
                    } else if (ViewPresenter.this.checkPermission()) {
                        ViewPresenter.this.enchantedViewPager.setDrawingCacheEnabled(true);
                        ViewPresenter viewPresenter3 = ViewPresenter.this;
                        viewPresenter3.saveImage(viewPresenter3.enchantedViewPager.getCurrentItem());
                    }
                } else if (ViewPresenter.this.checkPermission()) {
                    Intent intent = new Intent(ViewPresenter.this.activity, ZoomInActivity.class);
                    String sb = Value.assets_folder +
                            Value.arrayListDetail.get(ViewPresenter.this.enchantedViewPager.getCurrentItem()).wallpaper_image;
                    intent.putExtra("str_image", sb);
                    ViewPresenter.this.activity.startActivity(intent);
                }
            }

            public void onExpand() {
            }

            public void onCollapse() {
            }
        });
    }


    public void cropWallpaper() {
        String sb = Value.assets_folder +
                Value.arrayListDetail.get(this.enchantedViewPager.getCurrentItem()).wallpaper_image;
        ILogger.log(sb);
        Uri source = Uri.fromFile(saveImageCropped());
        new File(this.activity.getExternalCacheDir(), "cropped").mkdirs();
        advancedConfig(basisConfig(UCrop.of(source, Uri.fromFile(new File(this.activity.getExternalCacheDir(), "cropped/image.png"))))).start(this.activitys);
    }

    private UCrop basisConfig(UCrop uCrop) {
        return uCrop.useSourceImageAspectRatio();
    }

    private UCrop advancedConfig(UCrop uCrop) {
        Options options = new Options();
        options.setCompressionFormat(CompressFormat.PNG);
        return uCrop.withOptions(options);
    }


    public void showBottomSheetDialog(final Bitmap bitmap) {
        android.view.View view = this.activity.getLayoutInflater().inflate(R.layout.dialog_set_wallpaper, null);
        final BottomSheetDialog dialog_desc = new BottomSheetDialog(this.activity);
        dialog_desc.setContentView(view);
       // Objects.requireNonNull(dialog_desc.getWindow()).findViewById(R.id.design_bottom_sheet).setBackgroundResource(17170445);
        dialog_desc.show();
        TextView textHomeLockScreen = dialog_desc.findViewById(R.id.textHomeLockScreen);
        TextView textHomeScreenAndLockScreen = dialog_desc.findViewById(R.id.textHomeScreenAndLockScreen);
        Objects.requireNonNull((TextView) dialog_desc.findViewById(R.id.textHomeScreen)).setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
                new SetWallpaperTask(bitmap, "HOME").execute();
                dialog_desc.dismiss();
            }
        });
        Objects.requireNonNull(textHomeLockScreen).setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
                new SetWallpaperTask(bitmap, "LOCK").execute();
                dialog_desc.dismiss();
            }
        });
        Objects.requireNonNull(textHomeScreenAndLockScreen).setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
                new SetWallpaperTask(bitmap, "BOTH").execute();
                dialog_desc.dismiss();
            }
        });
    }


    public void saveImage(int position) {
        final String shareImage = Value.assets_folder +
                Value.arrayListDetail.get(position).wallpaper_image;
        final Context _context = ViewPresenter.this.activity.getApplicationContext();
        final File destDir = new File(_context.getExternalFilesDir(null), this.activity.getString(R.string.download_folder));
        final String fileName = Uri.parse(Value.arrayListDetail.get(position).wallpaper_image).getLastPathSegment();
        Glide.with(this.activity).load(shareImage).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(ViewPresenter.this.activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ViewPresenter.this.progress.show();
                String root = _context.getExternalFilesDir(null).getAbsolutePath() +
                        File.separator +
                        ViewPresenter.this.activity.getString(R.string.download_folder) +
                        File.separator;
                File rootFile = new File(root);
                if (!rootFile.exists()) {
                    rootFile.mkdir();
                }
                File directory = new File(root, shareImage.substring(shareImage.lastIndexOf("/")));
                try {
                    if (!directory.exists()) {
                        FileOutputStream out = new FileOutputStream(directory);
                        ViewPresenter.drawableToBitmap(resource).compress(CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                    }
                    MediaScannerConnection.scanFile(ViewPresenter.this.activity, new String[]{directory.getAbsolutePath()}, null, new OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
                    ViewPresenter.this.progress.stopLoader();
                    Snacky.builder().setActivity(ViewPresenter.this.activity).setActionText("OPEN").setActionClickListener(new OnClickListener() {
                        public void onClick(android.view.View v) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            String sb = destDir +
                                    File.separator +
                                    fileName;
                            intent.setDataAndType(Uri.fromFile(new File(sb)), "image/*");
                            ViewPresenter.this.activity.startActivity(intent);
                        }
                    }).setText(ViewPresenter.this.activity.getString(R.string.wallpaper_saved)).setDuration(Snacky.LENGTH_INDEFINITE).success().show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Snacky.builder().setActivity(ViewPresenter.this.activity).setText(ViewPresenter.this.activity.getString(R.string.wallpaper_saved_error)).setDuration(Snacky.LENGTH_LONG).error().show();
                    Toast.makeText(ViewPresenter.this.activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    ViewPresenter.this.progress.stopLoader();
                }
                return false;
            }
        }).preload();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public void setWallpaperGIF(int position) {
        String str = "SET_LOCKSCREEN_WALLPAPER";
        String str2 = ".data.services.GifWallpaperService";
        String str3 = "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT";
        String str4 = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER";
        String str5 = "set_wallpaper";
        this.progress.show();
        try {
            File destDir = new File(this.activity.getExternalCacheDir(), str5);
            String fileName = Uri.parse(Value.arrayListDetail.get(position).wallpaper_image).getLastPathSegment();
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            new AssetCopier(this.activity).withFileScanning().copy(Value.arrayListDetail.get(position).wallpaper_image, destDir);
            Pref pref = Pref.getSharedPref(this.activity);
            String sb = destDir + File.separator + fileName;
            pref.write(str5, sb);
            try {
                WallpaperManager.getInstance(this.activity).clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Intent intent = new Intent(str4);
                Activity activity2 = this.activity;
                String sb2 = this.activity.getPackageName() + str2;
                intent.putExtra(str3, new ComponentName(activity2, sb2));
                intent.putExtra(str, true);
                this.activity.startActivity(intent);
            } catch (Exception e2) {
                try {
                    this.activity.startActivity(new Intent("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER"));
                } catch (ActivityNotFoundException e3) {
                    try {
                        Intent intent2 = new Intent();
                        intent2.setAction("com.bn.nook.CHANGE_WALLPAPER");
                        this.activity.startActivity(intent2);
                    } catch (ActivityNotFoundException e4) {
                        try {
                            Intent intent3 = new Intent(str4);
                            Activity activity3 = this.activity;
                            String sb3 = this.activity.getPackageName() + str2;
                            intent3.putExtra(str3, new ComponentName(activity3, sb3));
                            intent3.putExtra(str, true);
                            this.activity.startActivity(intent3);
                        } catch (Exception ex) {
                            Toast.makeText(this.activity, "Your phone not support live wallpaper", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                }
            }
            this.progress.stopLoader();
        } catch (IOException e5) {
            e5.printStackTrace();
            Snacky.builder().setActivity(this.activity).setText(this.activity.getString(R.string.wallpaper_saved_error)).setDuration(Snacky.LENGTH_LONG).error().show();
            this.progress.stopLoader();
        }
    }


    public void shareImage(int position) {
        this.progress.show();
        try {
            File destDir = new File(this.activity.getExternalCacheDir(), "shared_folder");
            String fileName = Uri.parse(Value.arrayListDetail.get(position).wallpaper_image).getLastPathSegment();
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            new AssetCopier(this.activity).withFileScanning().copy(Value.arrayListDetail.get(position).wallpaper_image, destDir);
            this.progress.stopLoader();
            try {
                StrictMode.setVmPolicy(new Builder().build());
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("image/*");
                String sb = "file://" + destDir + File.separator + fileName;
                share.putExtra("android.intent.extra.STREAM", Uri.parse(sb));
                String sb2 = this.activity.getString(R.string.get_more_wall) +
                        "\n" +
                        this.activity.getString(R.string.app_name) +
                        " - https://play.google.com/store/apps/details?id=" +
                        this.activity.getPackageName();
                share.putExtra("android.intent.extra.TEXT", sb2);
                this.activity.startActivity(Intent.createChooser(share, "Share Wallpaper"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private File saveImageCropped() {
        try {
            this.enchantedViewPager.setDrawingCacheEnabled(true);
            Bitmap map = Bitmap.createBitmap(this.enchantedViewPager.getDrawingCache());
            File cachePath = new File(this.activity.getExternalCacheDir(), this.activity.getString(R.string.app_name));
            if (!cachePath.exists()) {
                cachePath.mkdirs();
            }
            String unsplitExtension = Value.arrayListDetail.get(this.enchantedViewPager.getCurrentItem()).wallpaper_image;
            String extension = unsplitExtension.substring(unsplitExtension.lastIndexOf("."));
            String sb = cachePath + File.separator + "uncropped" + extension;
            File output = new File(sb);
            FileOutputStream stream = new FileOutputStream(output);
            if (!extension.contains("jpg")) {
                if (!extension.contains("jpeg")) {
                    map.compress(CompressFormat.PNG, 100, stream);
                    stream.close();
                    return output;
                }
            }
            map.compress(CompressFormat.JPEG, 100, stream);
            stream.close();
            return output;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean checkPermission() {
        String str = "android.permission.WRITE_EXTERNAL_STORAGE";
        int checkSelfPermission = ContextCompat.checkSelfPermission(this.activity, str);
        if (checkSelfPermission == 0 || VERSION.SDK_INT < 23) {
            return true;
        }
        this.activity.requestPermissions(new String[]{str}, InputDeviceCompat.SOURCE_GAMEPAD);
        return false;
    }
}
