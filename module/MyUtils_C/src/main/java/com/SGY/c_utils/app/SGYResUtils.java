package com.SGY.c_utils.app;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.SGY.c_utils.SGYUtils;

/**
 * 从资源文件 res目录，获取数据
 */
public class SGYResUtils {

    /**
     * strings.xml
     */
    public static String getString(@StringRes int resId) {
        return getString(resId, null);
    }

    /**
     * strings.xml
     */
    public static String getString(@StringRes int resId, Object... formatArgs) {
        return SGYUtils.getContext().getString(resId, formatArgs);
    }

    /**
     * colors.xml
     */
    public static int getColor(@ColorRes int resId) {
        return SGYUtils.getContext().getResources().getColor(resId);
    }

    /**
     * dimens.xml 与getDimension()功能类似，不同的是将结果转换为int，并且小数部分四舍五入
     */
    public static int getDimensionPixelSize(@DimenRes int resId) {
        return SGYUtils.getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * dimens.xml 与getDimension()功能类似，不同的是将结果转换为int，并且偏移转换（offset conversion，函数命名中的offset是这个意思）是直接截断小数位，即取整（其实就是把float强制转化为int，注意不是四舍五入）
     */
    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return SGYUtils.getContext().getResources().getDimensionPixelOffset(resId);
    }

    /**
     * dimens.xml getDimension()是基于当前DisplayMetrics进行转换，获取指定资源id对应的尺寸。文档里并没说这里返回的就是像素，要注意这个函数的返回值是float，像素肯定是int
     */
    public static float getDimension(@DimenRes int resId) {
        return SGYUtils.getContext().getResources().getDimension(resId);
    }

    /**
     * drawable 目录下
     *
     * @param theme 可为空，也可设置主题
     */
    public static Drawable getDrawable(@DrawableRes int resId, @Nullable Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return SGYUtils.getContext().getResources().getDrawable(resId, theme);
        } else
            return SGYUtils.getContext().getResources().getDrawable(resId);
    }

    /**
     * drawable 目录下
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return SGYResUtils.getDrawable(resId, null);
    }
}
