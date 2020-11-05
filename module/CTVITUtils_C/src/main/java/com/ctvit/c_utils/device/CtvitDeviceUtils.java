package com.ctvit.c_utils.device;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;

import com.ctvit.c_utils.CtvitUtils;

/**
 * 设备信息相关工具类
 */
public class CtvitDeviceUtils {
    /**
     * 设备ID（IMEI > ANDROID_ID > SERIAL）
     */
    public static String getDeviceId() {
        Context context = CtvitUtils.getContext();
        String deviceId = "";
        try {
            if (context != null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                }
            }

            if (TextUtils.isEmpty(deviceId) || "000000000000000".equals(deviceId)) {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        try {
            if (TextUtils.isEmpty(deviceId) || "9774d56d682e549c".equals(deviceId)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    deviceId = Build.getSerial();
                else
                    deviceId = Build.SERIAL;
            }
        } catch (Exception e) {

        }
        return deviceId;
    }

    /**
     * 获取手机厂商
     *
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取当前系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取IMEI
     *
     * @return
     */
    public static String getImei() {
        String deviceId = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (ActivityCompat.checkSelfPermission(CtvitUtils.getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                    deviceId = ((TelephonyManager) CtvitUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            }
        } catch (Exception e) {

        }
        return deviceId;
    }

    /**
     * 获取ANDROID_ID
     *
     * @return
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(CtvitUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取serial
     *
     * @return
     */
    public static String getSerial() {
        String deviceId = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (ActivityCompat.checkSelfPermission(CtvitUtils.getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                    deviceId = Build.getSerial();
            }

            if (TextUtils.isEmpty(deviceId))
                deviceId = Build.SERIAL;
        } catch (Exception e) {

        }
        return deviceId;
    }

    /**
     * 获得分辨率
     */
    public static String getResolution() {
        WindowManager manager = (WindowManager) CtvitUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics.widthPixels + "*" + displaysMetrics.heightPixels;
    }
}
