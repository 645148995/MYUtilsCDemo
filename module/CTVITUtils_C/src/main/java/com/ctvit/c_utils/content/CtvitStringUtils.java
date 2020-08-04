package com.ctvit.c_utils.content;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CtvitStringUtils {

    /**
     * @param str 需要判断的字符
     * @return boolean true 是空. false 不是空
     * @author guoxi
     * @date 2020/7/28 14:15
     * @description 校验字符串，去掉所有空格后，是否为空
     **/
    public static boolean isTrimNull(String str) {
        if (str == null || TextUtils.isEmpty(str.toLowerCase().replace("null", "").replace(" ", "")))
            return true;
        return false;
    }

    /**
     * 判断一个字符是汉字还是字符
     *
     * @param c 需要判断的字符
     * @return boolean true 字符. false 汉字
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度，一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 验证手机号 return true 合法 false非法
     * 因目前手机号，号段太多，且新增频率较快。所以放宽了验证规则。例如11111111111，目前验证也是有效的
     */
    public static boolean isPhoneNumber(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        return str.matches("^[1][0-9][0-9]{9}$");
    }

    /**
     * 邮箱验证
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strEmail)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    /**
     * 只能输入半角字符 str不可为空 为空则返回false
     * <p>
     * return true都是半角，false有汉字或者全角字符
     * </p>
     */
    public static boolean isHalfCharacters(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        return str.matches("[\\x00-\\xff]+");
    }

    /**
     * 从URL中获取参数
     *
     * @param url http://url?aa=1&bb=2
     */
    public static Map<String, String> getUrlParams(String url) {
        if (TextUtils.isEmpty(url))
            return null;

        Map<String, String> map = new HashMap<>(10);
        String str = url.substring(url.indexOf("?") + 1);
        String[] params = str.split("&");
        for (String p : params) {
            String[] keyVal = p.split("=");
            if (keyVal.length > 1)
                map.put(keyVal[0], keyVal[1]);
            else
                map.put(keyVal[0], "");
        }
        return map;
    }

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String intIp2StringIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * @param str   原始字符串
     * @param count 要截取的字符数
     * @author guoxi
     * @date 2020/7/27 21:15
     * @description 按字符截取字符串，一个汉字或全角字符算2个字符
     **/
    public static String substring(String str, int count) {
        if (TextUtils.isEmpty(str))
            return "";

        StringBuffer buff = new StringBuffer();
        char c;
        int sum = 0;
        for (int i = 0; i < count; i++) {
            c = str.charAt(i);
            if (CtvitStringUtils.isLetter(c) && count - sum > 0) { //是半角字符，且还没达到count的数量
                buff.append(c);
                sum++;
            } else if (!CtvitStringUtils.isLetter(c) && count - sum > 1) { //是全角字符，且还没达到count的数量
                buff.append(c);
                sum += 2;
            } else {
                break;
            }
        }
        return buff.toString();
    }
}
