package com.ctvit.c_utils.device;

import android.content.Context;

import com.ctvit.c_utils.content.CtvitLogUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author yxl
 * @date 2020/7/15 14:22
 * @description 比例计算工具类
 **/
public class CtvitScaleUtils {

    /**
     * @param scaleW 宽比例
     * @param scaleH 高比例
     * @return 高度 单位 px
     * @author yxl
     * @date 2020/7/15 14:23
     * @description 根据屏幕宽度计算高度比例，用于宽大于高
     **/
    public static int countScale(int scaleW, int scaleH) {
        int sw = CtvitScreenUtils.getWidth();
        float scale = 1;
        try {
            scale = Float.valueOf(percnet(Double.valueOf(scaleW + ""),
                    Double.valueOf(scaleH + "")));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return Math.round(sw / scale);
    }

    /**
     * @param wDp    宽度 单位 dp
     * @param scaleW 宽比例
     * @param scaleH 高比例
     * @return 高度 单位 px
     * @author yxl
     * @date 2020/7/15 14:25
     * @description 根据指定宽度计算高度比例，用于宽大于高
     **/
    public static int countScaleOfWid(int wDp, int scaleW,
                                      int scaleH) {
        int wPx = CtvitDensityUtils.dpToPx(wDp);
        float scale = Float.valueOf(percnet(Double.valueOf(scaleW + ""),
                Double.valueOf(scaleH + "")));
        return Math.round(wPx / scale);
    }

    /**
     * @param scaleW   宽比例
     * @param scaleH   高比例
     * @param column   列数
     * @param marginDp 总边距
     * @return int[]{宽度 单位 px, 高度 单位 px}
     * @author yxl
     * @date 2020/7/15 14:26
     * @description 根据指定宽度计算高度比例，用于宽大于高
     **/
    public static int[] countScale(int scaleW, int scaleH,
                                   double column, int marginDp) {
        int marginPx = CtvitDensityUtils.dpToPx(marginDp);
        double widthPx = (CtvitScreenUtils.getWidth() - marginPx) / column;
        float scale = Float.valueOf(percnet(Double.valueOf(scaleW + ""),
                Double.valueOf(scaleH + "")));
        return new int[]{(int) widthPx, Math.round((int) widthPx / scale)};
    }

    /**
     * 根据屏幕宽度计算高度比例，用于宽大于高
     *
     * @param scaleW   宽比例
     * @param scaleH   高比例
     * @param marginPx 左右边距
     * @return 高度 单位 px
     */
    public static int countScale(int scaleW, int scaleH, int marginPx) {
        int sw = CtvitScreenUtils.getWidth() - marginPx;
        float scale = 1;
        try {
            scale = Float.valueOf(percnet(Double.valueOf(scaleW + ""), Double.valueOf(scaleH + "")));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return Math.round(sw / scale);
    }

    /**
     * @param scaleW   宽比例
     * @param scaleH   高比例
     * @param column   列数
     * @param marginDp 总边距
     * @return int[]{宽度 单位 px, 高度 单位 px}
     * @author yxl
     * @date 2020/7/15 14:27
     * @description 根据指定宽度计算高度比例，用于高大于宽
     **/
    public static int[] countScale2(int scaleW, int scaleH,
                                    int column, int marginDp) {
        int marginPx = CtvitDensityUtils.dpToPx(marginDp);
        int widthPx = (CtvitScreenUtils.getWidth() - marginPx) / column;
        float W = widthPx
                - widthPx
                * Float.valueOf(percnet(Double.valueOf(scaleW + ""),
                Double.valueOf(scaleH + "")));
        return new int[]{widthPx, Math.round(widthPx + W)};
    }

    /**
     * @param context 上下文对象
     * @param scaleW  宽比例
     * @param scaleH  高比例
     * @return 高度 单位 px
     * @author yxl
     * @date 2020/7/15 14:28
     * @description 根据屏幕宽度计算高度比例，用于高大于宽
     **/
    public static int countScale2(Context context, int scaleW, int scaleH) {
        int sw = CtvitScreenUtils.getWidth();
        float W = sw
                - sw
                * Float.valueOf(percnet(Double.valueOf(scaleW + ""),
                Double.valueOf(scaleH + "")));
        return Math.round(sw + W);
    }

    /**
     * @author yxl
     * @date 2020/7/15 14:29
     * @description 获取百分比
     **/
    private static String percnet(double d, double e) {
        double p = d / e;
        DecimalFormat nf = (DecimalFormat) NumberFormat.getPercentInstance();
        nf.applyPattern("00"); // 00表示小数点2位
        nf.setMaximumFractionDigits(2); // 2表示精确到小数点后2位
        return nf.format(p);
    }

}
