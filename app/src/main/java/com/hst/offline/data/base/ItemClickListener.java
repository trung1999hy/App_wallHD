package com.hst.offline.data.base;

import android.view.View;

public interface ItemClickListener<T> {
    void onItemClick(View view, T t, int i);
}
