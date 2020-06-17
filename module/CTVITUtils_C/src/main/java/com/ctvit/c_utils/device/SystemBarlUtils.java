package com.ctvit.c_utils.device;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * 状态栏相关工具类
 */
public final class SystemBarlUtils {

    /**
     * 显示/隐藏顶部通知栏
     */
    public static void showOrHiddenStatusBar(Activity activity, boolean show) {
        if (show) {
            if (Build.VERSION.SDK_INT >= 19) {
                View decorView = activity.getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                //| View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        } else
            setLandscapeModel(activity);
    }

    /**
     * 视频竖屏
     */
    public static boolean setPortraitModel(Activity activity) {
        if (activity == null)
            return false;

        View decorView = activity.getWindow().getDecorView();
//        if (Build.VERSION.SDK_INT >= 21) {
//            int option = //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            //activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            return true;
//        } else {
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//            return false;
//        }

        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().setStatusBarColor(Color.BLACK);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
        return false;
    }

    /**
     * 视频横屏
     */
    public static void setLandscapeModel(Activity activity) {
        if (activity == null)
            return;

        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}