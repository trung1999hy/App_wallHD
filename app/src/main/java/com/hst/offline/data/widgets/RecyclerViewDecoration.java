package com.hst.offline.data.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.State;

public class RecyclerViewDecoration extends ItemDecoration {
    private final int mItemOffset;

    public RecyclerViewDecoration(int itemOffset) {
        this.mItemOffset = itemOffset;
    }

    public RecyclerViewDecoration(Context context) {
        this(0);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int i = this.mItemOffset;
        outRect.set(i, i, i, i);
    }
}
