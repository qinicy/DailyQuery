package com.foxconn.crd.common.Utils;

import android.util.Log;

/**
 * Created by qinicy on 16/1/28.
 */
public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("L cannot be instantiated");
    }

    private static final String TAG = "";
    public static int level = 0;
    private static final int V = 0;
    private static final int D = 1;
    private static final int I = 2;
    private static final int W = 3;
    private static final int E = 4;

    public static void v(String msg) {
        if (level <= V)
            Log.v(TAG, msg);
    }

    public static void i(String msg) {
        if (level <= I)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (level <= D)
            Log.d(TAG, msg);
    }

    public static void w(String msg) {
        if (level <= W)
            Log.w(TAG, msg);
    }
    public static void e(String msg) {
        if (level <= E)
            Log.e(TAG, msg);
    }


    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (level <= I)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (level <= D)
            Log.i(tag, msg);
    }
    public static void w(String tag,String msg) {
        if (level <= W)
            Log.w(tag, msg);
    }
    public static void e(String tag, String msg) {
        if (level <= E)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (level <= V)
            Log.i(tag, msg);
    }

}
