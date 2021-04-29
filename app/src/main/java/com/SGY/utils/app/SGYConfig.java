package com.SGY.utils.app;

import android.app.Application;

import com.SGY.c_utils.BuildConfig;
import com.SGY.c_utils.SGYUtils;

/**
 * 这个类处理自己库的配置和初始化，像其它第三方的库，最好每个都建一个这样的类
 */
public class SGYConfig {

    private volatile static SGYConfig singleton = null;
    private Application context;

    public SGYConfig() {

    }

    public static SGYConfig getInstance() {
        if (singleton == null) {
            synchronized (SGYConfig.class) {
                if (singleton == null) {
                    singleton = new SGYConfig();
                }
            }
        }
        return singleton;
    }

    /**
     * 配置仓库
     */
    public void init(Application app) {
        this.context = app;
        initBase();
    }

    /**
     * 基础配置（也会对其它库产生作用）
     */
    private void initBase() {
        SGYUtils.init(context);
        SGYUtils.getInstance().debug(BuildConfig.DEBUG, null);
    }
}
