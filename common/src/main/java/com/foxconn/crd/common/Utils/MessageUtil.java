package com.foxconn.crd.common.Utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by qinicy on 16/1/28.
 */
public class MessageUtil {
    public static final void sendMessage(Handler mHandler, int what, long delayed) {
        sendMessage(mHandler, what, 0, 0, 0);

    }

    public static final void sendMessage(Handler mHandler, int what, Object obj, long delayed) {
        sendMessage(mHandler, what, 0, 0, obj, delayed);

    }

    public static final void sendMessage(Handler mHandler, int what, int arg1, int arg2, long delayed) {

        sendMessage(mHandler, what, arg1, arg2, null, delayed);
    }

    public static final void sendMessage(Handler mHandler, int what, int arg1, int arg2, Object obj, long delayed) {
        Message message = new Message();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.obj = obj;
        if (delayed == 0)
            mHandler.sendMessage(message);
        else
            mHandler.sendMessageDelayed(message, delayed);
    }


}
