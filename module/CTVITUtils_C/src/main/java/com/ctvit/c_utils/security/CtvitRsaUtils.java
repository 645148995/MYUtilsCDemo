package com.ctvit.c_utils.security;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import com.ctvit.c_utils.content.CtvitLogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA是一种非对称加密算法。现在，很多登陆表单的密码的都采用RSA加密
 * 使用RSA一般需要产生公钥和私钥，当采用公钥加密时，使用私钥解密；采用私钥加密时，使用公钥解密
 * <p>
 * Android 的默认加密填充 :rsa/ecb/nopadding
 * 而JDK默认的加密填充方式：RSA/ECB/PKCS1Padding（这个安全，一般语言默认也都采用这种）
 * </p>
 */
public class CtvitRsaUtils {

    /**
     * RSA算法
     */
    private static final String ALGORITHM_RSA = "RSA";
    /**
     * 加密填充方式
     */
    private static final String PADDING_RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";
    /**
     * 签名算法
     */
    private static final String ALGORITHM_SIGNATURE = "MD5withRSA";
    /**
     * 秘钥默认长度
     */
    private static final int DEFAULT_KEY_SIZE = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = (DEFAULT_KEY_SIZE / 8) - 11;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = DEFAULT_KEY_SIZE / 8;

    /**
     * 获取密钥对
     *
     * @param
     * @return java.security.KeyPair 密钥对
     */
    public static KeyPair getKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            generator.initialize(DEFAULT_KEY_SIZE);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return java.security.PrivateKey 私钥
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            byte[] decodedKey = Base64.decode(privateKey.getBytes(), Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return java.security.PublicKey 公钥
     */
    public static PublicKey getPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            byte[] decodedKey = Base64.decode(publicKey.getBytes(), Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * RSA按私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥
     * @return java.lang.String 加密数据
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            return encryptForSpilt(data, getPrivateKey(privateKey));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * RSA按公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return java.lang.String 加密数据
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            return encryptForSpilt(data, getPublicKey(publicKey));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * RSA按私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return java.lang.String 解密数据
     */
    public static String decryptByPrivateKey(String data, String privateKey) {
        try {
            return decryptForSpilt(data, getPrivateKey(privateKey));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * RSA按公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 公钥
     * @return java.lang.String 解密数据
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            return decryptForSpilt(data, getPublicKey(publicKey));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * RSA分段加密
     *
     * @param data 待加密数据
     * @param key  秘钥
     * @return java.lang.String 加密数据
     */
    private static String encryptForSpilt(String data, Key key) {
        ByteArrayOutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(PADDING_RSA_ECB_PKCS1);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int inputLen = data.getBytes().length;
            out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64.encodeToString(encryptedData, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            CtvitLogUtils.e(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * RSA分段解密
     *
     * @param data 待解密数据
     * @param key  秘钥
     * @return java.lang.String 解密数据
     */
    private static String decryptForSpilt(String data, Key key) {
        ByteArrayOutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(PADDING_RSA_ECB_PKCS1);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] dataBytes = Base64.decode(data, Base64.DEFAULT);
            int inputLen = dataBytes.length;
            out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {//StandardCharsets 包不支持 19 以下的API
                return new String(decryptedData, StandardCharsets.UTF_8.name());
            }else {
                return new String(decryptedData, Charset.forName("UTF-8"));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            CtvitLogUtils.e(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * RSA按私钥签名
     *
     * @param data       待加密数据
     * @param privateKey 私钥
     * @return java.lang.String 签名
     */
    public static String signByPrivateKey(String data, String privateKey) {
        try {
            return sign(data, getPrivateKey(privateKey));
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * 签名
     *
     * @param data 待签名数据
     * @param pKey 私钥
     * @return java.lang.String 签名
     */
    public static String sign(String data, Key pKey) {
        try {
            byte[] keyBytes = pKey.getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(ALGORITHM_SIGNATURE);
            signature.initSign(key);
            signature.update(data.getBytes());
            return Base64.encodeToString(signature.sign(), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            CtvitLogUtils.e(e);
        }
        return null;
    }

    /**
     * 验证签名
     *
     * @param data      原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return boolean 是否验签通过
     */
    public static boolean verifyByPublicKey(String data, String publicKey, String sign) {
        try {
            return verify(data, getPublicKey(publicKey), sign);
        } catch (Exception e) {
            CtvitLogUtils.e(e);
        }
        return false;
    }

    /**
     * 验证签名
     *
     * @param data      原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return boolean 是否验签通过
     */
    public static boolean verify(String data, PublicKey publicKey, String sign) {
        try {
            byte[] keyBytes = publicKey.getEncoded();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(ALGORITHM_SIGNATURE);
            signature.initVerify(key);
            signature.update(data.getBytes());
            return signature.verify(Base64.decode(sign.getBytes(), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            CtvitLogUtils.e(e);
        }
        return false;
    }

    public static String formatKey(String key) {
        if (TextUtils.isEmpty(key))
            return "";

        return key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\\\r", "")
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\t", "")
                .replaceAll(" ", "");
    }

    public static String formatCipherText(String cipherText) {
        if (TextUtils.isEmpty(cipherText))
            return "";
        return cipherText.replaceAll("[\\s*\t\n\r]", "");
    }

}
