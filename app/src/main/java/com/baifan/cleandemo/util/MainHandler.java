package com.baifan.cleandemo.util;

/**
 * Created by baifan on 2017/5/7.
 */

import android.os.Handler;
import android.os.Looper;

/**
 * Created by chances on 16/10/18.
 */
public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (null == instance) {
            synchronized (MainHandler.class) {
                if (null == instance) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }
    private MainHandler() {
        super(Looper.getMainLooper());
    }
}