package com.ctvit.c_utils.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.ctvit.c_utils.CtvitUtils;
import com.ctvit.c_utils.content.CtvitLogUtils;
import com.ctvit.c_utils.content.CtvitStringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络相关工具类
 */
public class CtvitNetUtils {

    /**
     * 判断是否有可用网络
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) CtvitUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为WIFI上网
     */
    public static boolean isWifi() {
        String type = getNetworkType();
        if ("WIFI".equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * 获得联网方式
     */
    public static String getNetworkType() {
        String strNetworkType = "";
        ConnectivityManager cm = (ConnectivityManager) CtvitUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                strNetworkType = "ETHERNET"; //网线
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by
                        // 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
                        // 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
                        // 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
                        // 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
                        // 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                || _strSubTypeName.equalsIgnoreCase("WCDMA")
                                || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            // strNetworkType = _strSubTypeName;
                            strNetworkType = "其它";
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取IP地址
     */
    public static String getIpAddress() {
        if (CtvitNetUtils.isNetworkAvailable()) {
            if (CtvitNetUtils.isWifi()) {
                WifiManager wifiManager = (WifiManager) CtvitUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = CtvitStringUtils.intIp2StringIp(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            } else {
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    CtvitLogUtils.e(e);
                }
            }
        }
        return null;
    }

    /**
     * 获取当前连接的wifi SSID
     */
    public static String getWifiSSID() {
        if (!isNetworkAvailable())
            return "";
        
        String ssId = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            ConnectivityManager cm = (ConnectivityManager) CtvitUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                ssId = info.getExtraInfo();
            }
        }

        if (TextUtils.isEmpty(ssId) || ssId.toLowerCase().contains("unknown")) {
            WifiManager wifiManager = (WifiManager) CtvitUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                ssId = wifiInfo.getSSID();
            }
        }

        if (TextUtils.isEmpty(ssId) || ssId.toLowerCase().contains("unknown")) {
            WifiManager wifiManager = (WifiManager) CtvitUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                if (wifiConfiguration.networkId == wifiInfo.getNetworkId()) {
                    ssId = wifiConfiguration.SSID;
                    break;
                }
            }
        }

        if (TextUtils.isEmpty(ssId) || ssId.toLowerCase().contains("unknown"))
            return "";

        ssId = ssId.replaceAll("\"", "");

        return ssId;
    }
}
