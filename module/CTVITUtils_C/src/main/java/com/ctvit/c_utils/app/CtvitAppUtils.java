package com.ctvit.c_utils.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.ctvit.c_utils.CtvitUtils;
import com.ctvit.c_utils.content.CtvitLogUtils;
import com.ctvit.c_utils.device.CtvitSDCardUtils;

import java.io.File;
import java.util.List;

/**
 * 获取应用本身的一些信息，或者APP本身的操作
 */

public final class CtvitAppUtils {

    /**
     * 根据URL，用APP外的应用打开URL（比如说系统自带浏览器）
     */
    public static void openUrl(String url, Context context) {
        if (TextUtils.isEmpty(url))
            return;
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 打开APK
     *
     * @param file APK在本地的路径
     */
    public static void openApk(Context context, String authority, File file) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            CtvitSDCardUtils.chmod("777", file.getParentFile());
            CtvitSDCardUtils.chmod("777", file);
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_INSTALL_PACKAGE);

        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 下面这句话必须写在上面flag的下面，否则安装apk后，会没有安装完成页面
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(context, authority, file);//BuildConfig.APPLICATION_ID
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 获取AndroidManifest.xml中的 应用版本包名
     */
    public static String getPackageName() {
        try {
            return getManifestTag().packageName;
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    /**
     * 获取AndroidManifest.xml中的 应用版本CODE
     */
    public static int getVersionCode() {
        try {
            return getManifestTag().versionCode;
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return -1;
        }
    }

    /**
     * AndroidManifest.xml中的 应用版本名称
     */
    public static String getVersionName() {
        try {
            return getManifestTag().versionName;
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageInfo packageInfo = getManifestTag();
            int labelRes = packageInfo.applicationInfo.labelRes;
            return CtvitUtils.getContext().getResources().getString(labelRes);
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * AndroidManifest.xml中的 manifest 标签
     */
    public static PackageInfo getManifestTag()
            throws Exception {
        Context context = CtvitUtils.getContext();
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(context.getPackageName(), 0);
    }

    /**
     * 获取ApplicationInfo对象，用于获得MetaData标签值
     */
    public static ApplicationInfo getMetaData()
            throws Exception {
        PackageManager packageManager = CtvitUtils.getContext().getPackageManager();
        return packageManager.getApplicationInfo(CtvitUtils.getContext().getPackageName(),
                PackageManager.GET_META_DATA);
    }

    /**
     * 判断Activity是否Destroy
     *
     * @param mActivity
     * @return true:已销毁
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null ||
                mActivity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断Activity是否处于栈顶
     *
     * @param name Activity的名称 Activity.class.getName() 获得即可
     * @return true在栈顶false不在栈顶
     */
    public static boolean isActivityTop(String name) {
        ActivityManager manager = (ActivityManager) CtvitUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        String n = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return n.equals(name);
    }

    /**
     * 用来判断activity是否位于栈顶
     *
     * @return
     */
    public static boolean isTopActivity() {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) CtvitUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        //这里MainActivity会一直处于栈顶 特殊处理一下
        if (cn.getPackageName().equals(CtvitUtils.getContext().getPackageName())) {
            isTop = true;
        }
        return isTop;
    }

    /**
     * 判断该进程是否是app进程
     *
     * @return
     */
    public static boolean isAppProcess() {
        String processName = getProcessName();
        if (TextUtils.isEmpty(processName) || !processName.equalsIgnoreCase(getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取进程名称
     *
     * @return
     */
    public static String getProcessName() {
        ActivityManager am = (ActivityManager) CtvitUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 判断service是否已经运行
     * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
     * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
     * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
     *
     * @param className Service的全名,例如PushService.class.getName()
     * @return true:Service已运行 false:Service未运行
     */
    public static boolean isServiceExisted(String className) {
        try {
            ActivityManager am = (ActivityManager) CtvitUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
            int myUid = android.os.Process.myUid();
            for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
                if (runningServiceInfo.service == null || TextUtils.isEmpty(runningServiceInfo.service.getClassName()))
                    continue;

                if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 把应用置为前台
     * moveTaskToFront()
     * moveTaskToBack()
     */
    public static void bringToFront() {
        ActivityManager activtyManager = (ActivityManager) CtvitUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activtyManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
            if (CtvitUtils.getContext().getPackageName().equals(runningTaskInfo.topActivity.getPackageName())) {
                activtyManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                return;
            }
        }
    }

    /**
     * 回到系统桌面
     */
    public static void backToHome(Activity activity) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(home);
    }

    /**
     * 发送邮件
     *
     * @param context 上下文对象
     * @param title   主题
     * @param content 正文
     */
    public static void sendEmail(Context context, String title, String content) {
        Uri uri = Uri.parse("mailto:");
        String[] email = {""};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, title); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, content); // 正文
        context.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }

    /**
     * 获取清单文件的metaData值
     */
    public static String getMetaDataValue(String key) {
        try {
            return getMetaData().metaData.getString(key);
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }
}
