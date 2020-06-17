package com.ctvit.utils;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ctvit.c_utils.device.CtvitNetSpeedUtils;
import com.ctvit.c_utils.device.CtvitSDCardUtils;
import com.ctvit.c_utils.file.CtvitSizeConverter;
import com.ctvit.c_utils.security.CtvitApkSignUtils;
import com.ctvit.c_utils.security.CtvitAppSignUtils;
import com.ctvit.c_utils.security.CtvitRsaUtils;
import com.ctvit.c_utils.time.CtvitTimeUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.GET_ACTIVITIES;

public class UtilsActivity extends AppCompatActivity {

    private CtvitNetSpeedUtils netSpeedUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  timeDemo();
        //signInfoDemo();
        //netSpeed();

        aaa();
    }

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/ZeLwTPPLSU7QGwv6tVgdawz9n7S2CxboIEVQlQ1USAHvBRlWBsU2l7+HuUVMJ5blqGc/5y3AoaUzPGoXPfIm0GnBdFL+iLeRDwOS1KgcQ0fIquvr/2Xzj3fVA1o4Y81wJK5BP8bDTBFYMVOlOoCc1ZzWwdZBYpb4FNxt//5dAwIDAQAB";
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANySV5exPmOuaD2AO4XYlnpC0Yz0zGIsisiR3C6jd9TDCXSgP/9NM5oplwt38TLWQtMv/yY42N5ShG1RUBfP4LBurr6Vuix3UhO1yk42Kk0fdht4jU9gJpD995dHJvMeKB/Ow2zekZQqAiTXUE+V3WxFd4Ll/4zfrNyLv65xAwHLAgMBAAECgYABuEZkkWY6Ir7/U8OjDIhH1cOKhNIfJdfDLpf36SBjEU4+kY1OMUzos9BQr5O64L7OpL4pjyHpwdz0eUDVnrJSyiScqI4cxIeXlmx4TpvlEz5+bE+Uw+VHEStRtCNTMbQgJStCazQu1lKTNAWhMYZuaRjS5l33SR7sjnu81Xa78QJBAPgYd75doCbbEVvyzgeTzVOMYfpjs0Ua3LDMgA0wJo4TgdRAhOOq4FXgCynHaFIsqOGBgWlGyHvaqPg8Z3HmVkkCQQDjmWHPde+v+LQwu5SuvLtX5uKrv91+uA9Bc8vZwwgqOMNnhZ4aqZABhAdX84D8vVHnmdPNck2dVB3UfhvPvEdzAkAAiKTxTuhCTSRba0lQMNw3Os0XKdG/60/gPYBlBt5+XcJgv4bJqpty89lyTJCH5Lq0SYAKxvE1ewH0DsU2NIWpAkAbba6l69aY5GYCwXhnnoMNUtvaSuMMlG3yO1xDwAdJ31FdNnMZT3n1VM31wXi3/LDUMmueBLppzWtS4mfDnzMVAkAoEz8mjEVRLzeJ4OlB3qwGMB6QlMTMBaplbl7AaHwEEQ/rGpjNu16veHkQTsvGtBFkoIaqrjMwiUHHq5aA4VlN";


    public void aaa() {
        // 签名
        String sign = "iKleREReSSJnZobVBb+UgSOyjaXhbuwCnljtlkZayRgSNMEkso1aTJgcZOAoDtNWH5toFIkjHDO9lKr/T89oqyBsZ8jd4R1eS0ALwXeHHIviFUSwZgQ+RyI7Za/Ihkuy9wPn9VagvgdcWe8yHIq64C45H/vUTv/R6kwY+bp3iCI=";
        // 加密文本
        String encryptText = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAynP734mGwD5tuZ5NNZ3LRUPiSJ1j548rMJ4PBPu4zj862feyyElMcu6w0fOObllAmxfhX8krVajVj7CaYzOlYQIDAQABAkEAgibWQfsT2ftIZxBR6qoqx7jAfJZSZ8cuqO60ESwSvuPZdZGHddAAi69r00HmQfbcTa+Vb9xXgAp7dUV1TsDnkQIhAPkoM3Hn2XVw+fP1jAwckml1uI3My6XoJsjXkF2/JSXzAiEA0ANpa8c8fop5lue+fGPUBiXPf1NmFVUw/ztRAB8GOFsCIQCLsE0/dFRH8sJAU49kDvTLKZQB9CrM5l4wRiMLXCownwIgH3kkrkYLBSp2kzqlO7TvE68M4jsAKyIks7m2/bVvhkCIQDQrw2hT//ycpaLHoSmJzt7FOncmod/ZaUOAFXkT0CtVA==";

        // 解密文本
        String decryptText = CtvitRsaUtils.decryptByPublicKey(encryptText, PUBLIC_KEY);
        // 验证签名
        boolean result = CtvitRsaUtils.verifyByPublicKey(decryptText, PUBLIC_KEY, sign);
        if (result) {
            System.out.println(String.format("首页文本可用，首页文本:[%s]", decryptText));
        } else {
            System.err.println("首页文本不可用，验证签名失败");
        }

        String enStr = CtvitRsaUtils.encryptByPrivateKey("士大夫夫士大夫撒旦范德萨夫士大夫第三方第三方士大夫第三方士大夫地方收到发顺丰士大夫都是反低俗发生的夫士大夫第三方收到夫士大夫士大夫是否收到夫士大夫收到房贷首付士大夫发发顺丰士大夫是大师傅士大夫大师傅士大夫大师傅士大夫士大夫是", PRIVATE_KEY);
        String denStr = CtvitRsaUtils.decryptByPublicKey(enStr, "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMpz+9+JhsA+bbmeTTWdy0VD4kidY+ePKzCeDwT7uM4/Otn3sshJTHLusNHzjm5ZQJsX4V/JK1Wo1Y+wmmMzpWECAwEAAQ==");
        System.out.println(enStr);
        System.out.println(denStr);
    }

    /**
     * 获取当前网速
     */
    private void netSpeed() {
        netSpeedUtils = new CtvitNetSpeedUtils();
        netSpeedUtils.setOnNetSpeedListener(new CtvitNetSpeedUtils.OnNetSpeedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void netSpeed(int netSpeed) {
                /**
                 * 这个回调是在子线程，如果有UI操作，请自行处理切换到UI线程
                 */
                String kb = CtvitSizeConverter.KBTrim.convert(netSpeed / 1024);
                System.out.println(kb + "当前网速：" + netSpeed);
                PackageManager pm = getPackageManager();
                ApplicationInfo ai = null;
                try {
                    ai = pm.getApplicationInfo(getPackageName(), GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println(ai.uid + " | " + android.os.Process.myUid());
                System.out.println(TrafficStats.getTotalRxBytes() + "TotalRxBytes：" + TrafficStats.getUidRxBytes(ai.uid));
            }
        }).startTimerTask();
        /*
         * 注意startTimerTask()后，不用时，一定要找机会调用cancelTimerTask()
         * */
    }

    /**
     * 获取签名信息
     */
    private void signInfoDemo() {
        List<String> list = CtvitApkSignUtils.getSingInfo(CtvitSDCardUtils.getExternalStorageDirectory() + "/CCTV5_CCTV_2019-01-23.apk", CtvitApkSignUtils.SHA256);
        for (String s : list) {
            System.out.println("1-=" + s);
        }
        System.out.println("2=" + CtvitApkSignUtils.sha1(CtvitSDCardUtils.getExternalStorageDirectory() + "/CCTV5_CCTV_2019-01-23.apk"));

        System.out.println("====================================================");

        System.out.println("3=" + CtvitAppSignUtils.md5());
        List<String> list2 = CtvitAppSignUtils.getSingInfo(CtvitAppSignUtils.SHA1);
        for (String s : list2) {
            System.out.println("4-=" + s);
        }
    }

    /**
     * 时间
     */
    private void timeDemo() {
        System.out.println(CtvitTimeUtils.getMillis("2019-04-02 13:09:09"));
        System.out.println(CtvitTimeUtils.format(System.currentTimeMillis() + 6 * 60 * 60 * 1000, CtvitTimeUtils.FORMATE_FULL.concat(" a")));
        System.out.println(CtvitTimeUtils.format("2019-04-02 13:09:09", "HH小时mm分钟ss秒"));
        Period period = CtvitTimeUtils.period(System.currentTimeMillis() - 62 * 60 * 1000, System.currentTimeMillis());
        System.out.println("相差的年数：" + period.getYears());
        System.out.println("相差的天数：" + period.getDays());
        System.out.println("相差的小时数：" + period.getHours());
        System.out.println("相差的分钟：" + period.getMinutes());
        System.out.println("相差的秒：" + period.getSeconds());
        System.out.println("===================================================");
        Period perio2 = CtvitTimeUtils.period("2018-04-02 13:09:09", "2019-04-02 14:10:10");
        System.out.println("相差的年数：" + perio2.getYears());
        System.out.println("相差的天数：" + perio2.getDays());
        System.out.println("相差的小时数：" + perio2.getHours());
        System.out.println("相差的分钟：" + perio2.getMinutes());
        System.out.println("相差的秒：" + perio2.getSeconds());
        System.out.println("===================================================");
        Duration duration = CtvitTimeUtils.duration("2018-04-02 13:09:09", "2019-04-02 14:10:10");
        System.out.println("相差的天数：" + duration.getStandardDays());
        System.out.println("相差的小时数：" + duration.getStandardHours());
        System.out.println("相差的分钟：" + duration.getStandardMinutes());
        System.out.println("相差的秒：" + duration.getStandardSeconds());
        System.out.println("===================================================");
        Interval interval = CtvitTimeUtils.interval("2018-04-02 13:09:09", "2019-04-02 14:10:10");
        System.out.println("当前时间是否在指定的时间段内：" + interval.containsNow());
        System.out.println("指定时间是否在指定的时间段内：" + interval.contains(new DateTime("2019-04-03")));
        System.out.println("指定时间是否在指定的时间段内：" + interval.contains(CtvitTimeUtils.getMillis("2019-04-03 15:10:10")));
        System.out.println("===================================================");
        Interval interval2 = CtvitTimeUtils.interval(System.currentTimeMillis() - 62 * 60 * 1000, System.currentTimeMillis() + 62 * 60 * 1000);
        System.out.println("当前时间是否在指定的时间段内：" + interval2.containsNow());
        System.out.println("指定时间是否在指定的时间段内：" + interval2.contains(System.currentTimeMillis() + 63 * 60 * 1000));
        System.out.println("指定时间是否在指定的时间段内：" + interval2.contains(new DateTime("2019-04-03")));
        System.out.println("指定时间是否在指定的时间段内：" + interval2.contains(CtvitTimeUtils.getMillis("2019-04-03 15:10:10")));
        System.out.println("===================================================");
        System.out.println(CtvitTimeUtils.duration(62 * 1000, true));
        //参考示例：https://blog.csdn.net/wjsshhx/article/details/62422844
        //参考示例：https://blog.csdn.net/weixin_37364740/article/details/83783873
        //参考示例：https://blog.csdn.net/qq_20417499/article/details/82225296
        //也可以自己查查 Joda-Tome DateTime
        DateTime dateTime = new DateTime();
        System.out.println("当前月的第几天：" + dateTime.getDayOfMonth());
        System.out.println("当前年的第几天：" + dateTime.getDayOfYear());
        System.out.println("当前月份：" + dateTime.monthOfYear().get());
        System.out.println("当前年份：" + dateTime.year().get());
        System.out.println("根据指定月份返回当月有多少天：" + dateTime.withMonthOfYear(2).dayOfMonth().getMaximumValue());
        System.out.println("获得星期几（星期几）：" + dateTime.withDayOfWeek(6).dayOfWeek().getAsShortText(Locale.CHINESE));
//        判断DateTime对象大小状态的一些操作方法
//        compareTo(DateTime d)           比较两时间大小 时间大于指定时间返回 1 时间小于指定时间返回-1 相等返回0
//        equals(DateTime d)              比较两时间是否相等
//        isAfter(long instant)           判断时间是否大于指定时间
//        isAfterNow()                    判断时间是否大于当前时间
//        isBefore(long instant)          判断时间是否小于指定时间
//        isBeforeNow()                   判断时间是否小于当前时间
//        isEqual(long instant)           判断时间是否等于指定时间
//        isEqualNow()                    判断时间是否等于当前时间
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (netSpeedUtils != null)
//            netSpeedUtils.startTimerTask();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (netSpeedUtils != null)
//            netSpeedUtils.cancelTimerTask();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netSpeedUtils != null)
            netSpeedUtils.cancelTimerTask();
    }
}
