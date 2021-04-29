package com.SGY.c_utils.security;

import android.text.TextUtils;

import com.SGY.c_utils.content.SGYLogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 获取APK签名信息
 */
public class SGYApkSignUtils {

    public final static String MD5 = "MD5", SHA1 = "SHA1", SHA256 = "SHA256";

    public static String md5(String apkPath) {
        return SGYApkSignUtils.getSing(SGYApkSignUtils.getSingInfo(apkPath, MD5));
    }

    public static String sha1(String apkPath) {
        return SGYApkSignUtils.getSing(SGYApkSignUtils.getSingInfo(apkPath, SHA1));
    }

    public static String sha256(String apkPath) {
        return SGYApkSignUtils.getSing(SGYApkSignUtils.getSingInfo(apkPath, SHA256));
    }

    private static String getSing(List<String> list) {
        if (list == null || list.isEmpty())
            return "";
        return list.get(0);
    }

    /**
     * 从APK中读取签名
     *
     * @param apkPath
     */
    public static List<String> getSingInfo(String apkPath, String type) {
        List<String> list = new ArrayList<>();
        try {
            File file = new File(apkPath);
            if (file.isFile()) {
                JarFile jarFile = new JarFile(file);
                JarEntry je = jarFile.getJarEntry("AndroidManifest.xml");
                byte[] readBuffer = new byte[8192];
                Certificate[] certs = loadCertificates(jarFile, je, readBuffer);
                if (certs != null) {
                    for (Certificate c : certs) {
                        String tmp = null;
                        if (MD5.equals(type)) {
                            tmp = SGYDigestUtils.md5(c.getEncoded());
                        } else if (SHA1.equals(type)) {
                            tmp = SGYDigestUtils.sha1(c.getEncoded());
                        } else if (SHA256.equals(type)) {
                            tmp = SGYDigestUtils.sha256(c.getEncoded());
                        }
                        if (!TextUtils.isEmpty(tmp))
                            list.add(tmp);
                    }
                }
            } else {
                SGYLogUtils.e("获取签名的文件不存在");
            }
        } catch (Exception e) {
            SGYLogUtils.e(e);
        }
        return list;
    }

    /**
     * 加载签名
     *
     * @param jarFile
     * @param je
     * @param readBuffer
     */
    private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je, byte[] readBuffer) {
        try {
            InputStream is = jarFile.getInputStream(je);
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
            }
            is.close();
            return je != null ? je.getCertificates() : null;
        } catch (IOException e) {
            SGYLogUtils.e(e);
        }
        return null;
    }
}
