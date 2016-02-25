package com.foxconn.crd.common.adapter;

import android.view.View;

/**
 * Created by qinicy on 16/1/28.
 */
public interface OnItemClickListener<T> {
    public void onClick(View view, T obj);
}
