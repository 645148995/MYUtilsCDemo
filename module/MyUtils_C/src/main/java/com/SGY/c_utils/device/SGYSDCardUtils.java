package com.SGY.c_utils.device;

import android.content.Context;
import android.os.Environment;

import com.SGY.c_utils.SGYUtils;
import com.SGY.c_utils.content.SGYLogUtils;

import java.io.File;

/**
 * 设备存储相关工具类
 */

public final class SGYSDCardUtils {
    /**
     * 判断SDCard是否可用
     *
     * @return true可用 false不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     * 请查看 getDiskDir()
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取系统存储路径
     * 请查看 getDiskDir()
     */
    public static String getRootDirectory() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 对应手机里的清除缓存
     * 获得磁盘缓存目录
     * 当外部存储存在，且不可移除的时候获取的是/storage/emulated/0/Android/data/package/cache
     * 反之则是/data/user/0/package/cache
     */
    public static String getDiskCacheDir() {
        Context context = SGYUtils.getContext();
        String cachePath;
        if (isSDCardEnable() || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }

    /**
     * 获取拓展存储Cache的绝对路径
     */
    public static String getExternalCacheDir() {
        StringBuilder sb = new StringBuilder();
        File file = SGYUtils.getContext().getExternalCacheDir();
        if (file != null) {
            sb.append(file.getAbsolutePath());
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath())
                    .append("/Android/data/")
                    .append(SGYUtils.getContext().getPackageName())
                    .append("/cache").toString();
        }
        return sb.toString();
    }

    /**
     * 获得磁盘目录
     */
    public static String getDiskDir() {
        if (isSDCardEnable() || !Environment.isExternalStorageRemovable()) {
            return getExternalStorageDirectory();
        } else {
            return getRootDirectory();
        }
    }

    /**
     * 对应手机里的清除数据
     *
     * @return /storage/emulated/0/Android/data/包名/files 或 /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalFilesDir() {
        File file = SGYUtils.getContext().getExternalFilesDir("");
        return file != null ? file.getAbsolutePath() : getDiskCacheDir();
    }

    /**
     * 修改文件权限
     */
    public static void chmod(String permissions, File file) {
        try {
            Process p = Runtime.getRuntime().exec("chmod " + permissions + " " + file);
            p.waitFor();
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
    }
}
