package com.SGY.c_utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.SGY.c_utils.content.SGYLogUtils;

public class SGYUtils {
    private volatile static SGYUtils singleton = null;
    private static Application context;
    private static boolean isDebug;

    public SGYUtils() {

    }

    public static SGYUtils getInstance() {
        if (singleton == null) {
            synchronized (SGYUtils.class) {
                if (singleton == null) {
                    singleton = new SGYUtils();
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
     * @param tag   过滤日志的tag，默认为“sgy_”+ tag
     */
    public void debug(boolean debug, String tag) {
        String tempTag = TextUtils.isEmpty(tag) ? SGYLogUtils.customTagPrefix : tag;
        SGYLogUtils.customTagPrefix = tempTag;
        getInstance().isDebug = debug;
    }

    private static void testInitialize() {
        //java 1.8以下不支持此方法，先注释掉
        //Objects.requireNonNull(context, "请先在全局Application中调用 Utils.init() 初始化！");
    }
}
