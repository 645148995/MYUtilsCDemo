package com.ctvit.c_utils.security;

import android.text.TextUtils;
import android.util.Base64;

import com.ctvit.c_utils.content.CtvitLogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常用的编码方法的工具类包
 */
public class CtvitDigestUtils {

    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    public static String md5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    public static String sha1(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    public static String sha1(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(bytes);
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    public static String sha256(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    public static String sha256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            return byte2Hex(bits);
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
            return null;
        }
    }

    /**
     * 将byte转换为16进制
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int a = bytes[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * Base64编码
     */
    public static String encoderBase64(String str) {
        if (TextUtils.isEmpty(str))
            return str;
        try {
            return encoderBase64(str.getBytes());
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * Base64编码
     */
    public static String encoderBase64(byte[] input) {
        if (input == null)
            return "";
        try {
            return Base64.encodeToString(input, Base64.DEFAULT);
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * Base64解码
     */
    public static String decoderBase64(String str) {
        if (TextUtils.isEmpty(str))
            return str;
        try {
            return decoderBase64(str.getBytes());
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * Base64解码
     */
    public static String decoderBase64(byte[] input) {
        if (input == null)
            return "";
        try {
            return new String(Base64.decode(input, Base64.DEFAULT));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * url编码
     *
     * @param str
     * @param enc UTF-8
     */
    public static String encoderUrl(String str, String enc) {
        try {
            return URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * url解码
     *
     * @param str
     * @param enc UTF-8
     */
    public static String decoderUrl(String str, String enc) {
        try {
            return URLDecoder.decode(str, enc);
        } catch (UnsupportedEncodingException e) {
            CtvitLogUtils.e(e);
            return "";
        }
    }

    /**
     * Unicode 编码（把每个字符转成16进制的字符串）
     */
    public static String unicode(String source) {
        StringBuffer sb = new StringBuffer();
        char[] source_char = source.toCharArray();
        String unicode;
        for (int i = 0; i < source_char.length; i++) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        return sb.toString();
    }

    /**
     * Unicode 解码 （把16进制的字符串转为int数字之后强转位char）
     */
    public static String decoderUnicode(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();
    }
}
