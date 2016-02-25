package com.foxconn.crd.common.Utils;

import android.view.View;

/**
 * Created by qinicy on 16/1/28.
 */
public class ViewUtil {
    public static final <T extends View> T findViewById(View view,int id){
        return (T) view.findViewById(id);
    }
}
