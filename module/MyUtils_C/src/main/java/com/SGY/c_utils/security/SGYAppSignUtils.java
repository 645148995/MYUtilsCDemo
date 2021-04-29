package com.SGY.c_utils.security;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

import com.SGY.c_utils.SGYUtils;
import com.SGY.c_utils.content.SGYLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取APP签名信息
 */
public class SGYAppSignUtils {

    public final static String MD5 = "MD5", SHA1 = "SHA1", SHA256 = "SHA256";

    public static String md5() {
        return SGYAppSignUtils.getSing(SGYAppSignUtils.getSingInfo(SGYAppSignUtils.MD5));
    }

    public static String sha1() {
        return SGYAppSignUtils.getSing(SGYAppSignUtils.getSingInfo(SGYAppSignUtils.SHA1));
    }

    public static String sha256() {
        return SGYAppSignUtils.getSing(SGYAppSignUtils.getSingInfo(SGYAppSignUtils.SHA256));
    }

    private static String getSing(List<String> list) {
        if (list == null || list.isEmpty())
            return "";
        return list.get(0);
    }

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param type
     */
    public static List<String> getSingInfo(String type) {
        List<String> signList = new ArrayList<>();
        try {
            Signature[] signs = SGYAppSignUtils.getSignatures();
            if (signs == null)
                return null;

            for (Signature sig : signs) {
                String tmp = null;
                if (MD5.equals(type)) {
                    tmp = SGYDigestUtils.md5(sig.toByteArray());
                } else if (SHA1.equals(type)) {
                    tmp = SGYDigestUtils.sha1(sig.toByteArray());
                } else if (SHA256.equals(type)) {
                    tmp = SGYDigestUtils.sha256(sig.toByteArray());
                }
                if (!TextUtils.isEmpty(tmp))
                    signList.add(tmp);
            }
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return signList;
    }

    /**
     * 返回对应包的签名信息
     */
    public static Signature[] getSignatures() {
        PackageInfo packageInfo;
        try {
            packageInfo = SGYUtils.getContext().getPackageManager().getPackageInfo(SGYUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            SGYLogUtils.e(e);
        }
        return null;
    }
}
