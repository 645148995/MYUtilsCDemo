package com.ctvit.c_utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.ctvit.c_utils.content.CtvitLogUtils;

public class CtvitUtils {
    private volatile static CtvitUtils singleton = null;
    private static Application context;
    private static boolean isDebug;

    public CtvitUtils() {

    }

    public static CtvitUtils getInstance() {
        if (singleton == null) {
            synchronized (CtvitUtils.class) {
                if (singleton == null) {
                    singleton = new CtvitUtils();
                }
            }
        }
        return singleton;
    }

    /**
     * 必须在全局Application先调用，获取context上下文
     */
    public static void init(Application app) {
        context = app;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        testInitialize();
        return context;
    }

    public static boolean isDebug() {
        return getInstance().isDebug;
    }

    /**
     * @param debug 是否为 debug 包
     * @param tag   过滤日志的tag，默认为“ctvit_”+ tag
     */
    public void debug(boolean debug, String tag) {
        String tempTag = TextUtils.isEmpty(tag) ? CtvitLogUtils.customTagPrefix : tag;
        CtvitLogUtils.customTagPrefix = tempTag;
        getInstance().isDebug = debug;
    }

    private static void testInitialize() {
        //java 1.8以下不支持此方法，先注释掉
        //Objects.requireNonNull(context, "请先在全局Application中调用 CtvitUtils.init() 初始化！");
    }
}
