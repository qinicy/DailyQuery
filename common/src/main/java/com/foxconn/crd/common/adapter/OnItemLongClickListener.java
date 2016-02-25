package com.foxconn.crd.common.adapter;

import android.view.View;

/**
 * Created by qinicy on 16/1/28.
 */
public interface OnItemLongClickListener<T> {
    public void onLongClick(View view, T obj);
}
