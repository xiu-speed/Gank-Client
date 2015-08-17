package com.speed.gank.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by huben on 15/8/13.
 */
public class ToastUtils {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
