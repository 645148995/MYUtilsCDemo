package com.ctvit.c_utils.app;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

import com.ctvit.c_utils.file.CtvitSPUtils;

/**
 * @author guoxi
 * @date 2021/2/25 15:47
 * @description 主题相关工具类
 **/
public class ThemeUtils {

    private static final String THEME_KEY = "APP_THEME_KEY";

    /**
     * @author guoxi
     * @date 2021/2/25 16:18
     * @description 是否为哀悼色
     **/
    public static boolean isAiDao() {
        return "ADS".equals(CtvitSPUtils.get(THEME_KEY, ""));
    }

    /**
     * @author guoxi
     * @date 2021/2/25 16:18
     * @description 是否为哀悼色
     **/
    public static void saveTheme(String theme) {
        if (theme == null)
            theme = "";
        CtvitSPUtils.put(THEME_KEY, theme);
    }

    /**
     * @param view 需要设置哀悼的view 如果不需要刻意指定某个view（也就是全局应用）就传null
     * @description 为页面设置哀悼色样式
     */
    public static void setAidaoColorStyle(Activity activity, View view) {
        if (view == null)
            view = activity.getWindow().getDecorView();
        if (ThemeUtils.isAiDao()) {
            //如果是哀悼色样式
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            view.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        } else {
            //不是哀悼色样式
            view.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

}
