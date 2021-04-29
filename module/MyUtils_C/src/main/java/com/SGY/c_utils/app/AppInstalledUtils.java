package com.SGY.c_utils.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.SGY.c_utils.SGYUtils;

import java.util.List;
import java.util.Locale;

/**
 * 检查某个APP是否安装
 */
public class AppInstalledUtils {

    /**
     * QQ 包名
     */
    public static final String APP_PACKAGENAME_QQ = "com.tencent.mobileqq";
    /**
     * 微信 包名
     */
    public static final String APP_PACKAGENAME_WECHAT = "com.tencent.mm";
    /**
     * 新浪微博 包名
     */
    public static final String APP_PACKAGENAME_SINA_WEIBO = "com.sina.weibo";

    /**
     * 判断是否安装了某个APP
     *
     * @param packageName 要检测应用的包名
     * @return true安装，false未安装
     */
    public static boolean isAppInstalled(String packageName) {
        final PackageManager packageManager = SGYUtils.getContext().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName.toLowerCase(Locale.ENGLISH);
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
