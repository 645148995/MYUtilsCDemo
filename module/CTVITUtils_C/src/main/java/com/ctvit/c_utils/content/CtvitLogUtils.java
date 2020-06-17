package com.ctvit.c_utils.content;

import android.text.TextUtils;
import android.util.Log;

import com.ctvit.c_utils.CtvitUtils;

/**
 * <p>tag规则为ctvitlog_日志出入的类名</p>
 * <p>3000个字符换行打印</p>
 */
public class CtvitLogUtils {

    //规定每段显示的长度
    private static final int LOG_MAXLENGTH = 3000; //其实默认是4000，但是写3900打印还是不全
    public static String customTagPrefix = "ctvit_";
    private static String className;//类名
    private static String methodName;//方法名
    private static String threadInfo;//线程的名称、优先级和线程组
    private static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer(3000);
        buffer.append("(").append(lineNumber).append(")");
        buffer.append(methodName);
        buffer.append("（").append(threadInfo).append("）");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getRunInfo(StackTraceElement[] sElements) {
        className = customTagPrefix + sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
        threadInfo = Thread.currentThread().toString();
    }

    public static void e(String message, Throwable tr) {
        if (!CtvitUtils.isDebug())
            return;

        if (TextUtils.isEmpty(message))
            message = "";

        getRunInfo(new Throwable().getStackTrace());
        Log.e(className, createLog(message), tr);
    }

    public static void e(Throwable tr) {
        if (!CtvitUtils.isDebug())
            return;

        getRunInfo(new Throwable().getStackTrace());
        Log.e(className, createLog(""), tr);
    }

    /**
     * 用于超过长度的Log打印 处理是换行打印
     */
    public static void e(String msg) {
        if (!CtvitUtils.isDebug())
            return;

        if (TextUtils.isEmpty(msg))
            msg = "";

        getRunInfo(new Throwable().getStackTrace());

        int strLength = msg.length();
        if (strLength <= LOG_MAXLENGTH) {
            Log.e(className, createLog(msg));
            return;
        }

        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(className, "(" + lineNumber + ")" + methodName + "[" + i + "]" + msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(className, "(" + lineNumber + ")" + methodName + "[" + i + "]" + msg.substring(start, strLength));
                break;
            }
        }
    }

    /**
     * 用于超过长度的Log打印 处理是换行打印
     */
    public static void i(String msg) {
        if (!CtvitUtils.isDebug())
            return;

        if (TextUtils.isEmpty(msg))
            msg = "";

        getRunInfo(new Throwable().getStackTrace());

        int strLength = msg.length();
        if (strLength <= LOG_MAXLENGTH) {
            Log.i(className, createLog(msg));
            return;
        }

        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.i(className, "(" + lineNumber + ")" + methodName + "[" + i + "]" + "（" + threadInfo + "）" + msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.i(className, "(" + lineNumber + ")" + methodName + "[" + i + "]" + "（" + threadInfo + "）" + msg.substring(start, strLength));
                break;
            }
        }
    }
}
