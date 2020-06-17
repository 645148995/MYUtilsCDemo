package com.ctvit.utils.app;

import android.app.Application;

import com.ctvit.c_utils.BuildConfig;
import com.ctvit.c_utils.CtvitUtils;

/**
 * 这个类处理自己库的配置和初始化，像其它第三方的库，最好每个都建一个这样的类
 */
public class CtvitConfig {

    private volatile static CtvitConfig singleton = null;
    private Application context;

    public CtvitConfig() {

    }

    public static CtvitConfig getInstance() {
        if (singleton == null) {
            synchronized (CtvitConfig.class) {
                if (singleton == null) {
                    singleton = new CtvitConfig();
                }
            }
        }
        return singleton;
    }

    /**
     * 配置Ctvit仓库
     */
    public void init(Application app) {
        this.context = app;
        initBase();
    }

    /**
     * 基础配置（也会对其它库产生作用）
     */
    private void initBase() {
        CtvitUtils.init(context);
        CtvitUtils.getInstance().debug(BuildConfig.DEBUG, null);
    }
}
