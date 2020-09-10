package com.ctvit.c_utils.app;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Doris
 * @date 2020/9/10 14:56
 * @description
 **/
public class CtvitPreventClickUtils {
    private static CtvitPreventClickUtils instance = null;

    private CtvitPreventClickUtils() {

    }

    public static CtvitPreventClickUtils getInstance() {
        if (instance == null) {
            synchronized (CtvitPreventClickUtils.class) {
                if (instance == null) {
                    instance = new CtvitPreventClickUtils();
                }
            }
        }

        return instance;
    }

    private Map<String, Long> tsMap = new HashMap<>();

    public void record(String key) {
        tsMap.put(key, System.currentTimeMillis());
    }

    public Long calculateCostTime(String key) {
        if (tsMap.containsKey(key)) {
            return Math.abs(System.currentTimeMillis() - tsMap.get(key));
        }
        return -1l;
    }

    public boolean isFastClick(View view, long delayTime) {

        String key = null;
        if (view == null) {
            key = "Common";
        } else {
            key = view.hashCode() + "";
        }

        if (!tsMap.containsKey(key)) {
            record(key);
            return false;
        } else {
            if (Math.abs(System.currentTimeMillis() - tsMap.get(key)) > delayTime) {
                record(key);
                return false;
            }
        }
        return true;
    }

    public boolean isFastClick(View view) {

        return isFastClick(view, 800);
    }
}
