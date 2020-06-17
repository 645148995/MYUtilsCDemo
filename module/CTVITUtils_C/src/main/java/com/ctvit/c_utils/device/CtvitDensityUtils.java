package com.ctvit.c_utils.device;

import com.ctvit.c_utils.CtvitUtils;

public class CtvitDensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(float dp) {
        float scale = CtvitUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxToDp(float px) {
        float scale = CtvitUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));

    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int pxToSp(float pxValue) {
        final float fontScale = CtvitUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int spToPx(float spValue) {
        final float fontScale = CtvitUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
