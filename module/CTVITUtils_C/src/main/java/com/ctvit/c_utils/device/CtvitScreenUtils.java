package com.ctvit.c_utils.device;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ctvit.c_utils.CtvitUtils;
import com.ctvit.c_utils.content.CtvitLogUtils;

/**
 * 获得屏幕相关的工具类.
 */

public class CtvitScreenUtils {
    /**
     * 获得屏幕宽度（屏幕的显示区域）
     */
    public static int getWidth() {
        WindowManager wm = (WindowManager) CtvitUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度（屏幕的显示区域）
     */
    public static int getHeight() {
        WindowManager wm = (WindowManager) CtvitUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusBarHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = CtvitUtils.getContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return statusHeight;
    }

    /**
     * 获取屏幕相关信息
     */
    public static DisplayMetrics getDisplay() {
        WindowManager wm = (WindowManager) CtvitUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }
}
