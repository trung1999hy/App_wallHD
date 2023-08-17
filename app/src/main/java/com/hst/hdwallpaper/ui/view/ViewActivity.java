package com.hst.hdwallpaper.ui.view;

import android.content.Intent;
import android.provider.MediaStore.Images.Media;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.hst.hdwallpaper.R;
import com.hst.hdwallpaper.data.base.BaseActivity;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;

public class ViewActivity extends BaseActivity<View, ViewPresenter> implements View {
    public AllAngleExpandableButton buttonExpandable;
    public ViewPager enchantedViewPager;
    public ImageView imageBack;

    public int getLayoutId() {
        return R.layout.activity_detail;
    }


    public void onStarting() {
        this.buttonExpandable = findViewById(R.id.button_expandable);
        this.enchantedViewPager = findViewById(R.id.enchantedViewPager);
        this.imageBack = findViewById(R.id.imageBack);
        this.presenter.initView(this, this, buttonExpandable, enchantedViewPager,
                imageBack, getIntent().getIntExtra("position", 0));
    }


    public void onDestroyed() {
    }

    public void onImageBack() {
        onBackPressed();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 69) {
            try {
                this.presenter.showBottomSheetDialog(Media.getBitmap(getContentResolver(), UCrop.getOutput(data)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 96) {
            UCrop.getError(data);
        }
    }

    public ViewPresenter initPresenter() {
        return new ViewPresenter();
    }
}
