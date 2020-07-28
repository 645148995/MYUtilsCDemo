package com.ctvit.c_utils.device;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ctvit.c_utils.content.CtvitLogUtils;

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

    /**
     * @param windowManager
     * @author guoxi
     * @date 2020/7/28 14:20
     * @description 判断底部navigator是否已经显示（比如华为手机底部导航）
     **/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    /**
     * @param activity
     * @author guoxi
     * @date 2020/7/28 14:21
     * @description 设置状态栏颜色，在Activity setContentView之前调用
     **/
    public static void setColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                WindowManager wmManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                if (hasSoftKeys(wmManager)) {
                    //有虚拟键的取消状态栏渲染防止底部导航栏被虚拟键遮挡
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                    }
                }
            }
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
    }
}