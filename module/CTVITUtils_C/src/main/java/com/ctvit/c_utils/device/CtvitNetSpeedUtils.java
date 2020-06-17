package com.ctvit.c_utils.device;

import android.net.TrafficStats;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 获取当前下载网速
 */
public class CtvitNetSpeedUtils {

    private long oldBytes;
    private int refreshTime = 1; //单位：秒
    private OnNetSpeedListener listener;
    private Timer timer;

    public CtvitNetSpeedUtils() {

    }

    /**
     * 设置几秒刷新一次，默认1秒
     */
    public CtvitNetSpeedUtils setRefreshTime(int second) {
        this.refreshTime = second;
        return this;
    }

    private long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes();
    }
    /**
     * 获取当前网速
     */
    private int getNetSpeed() {
        long newBytes = getTotalRxBytes();
        long finalBytes = newBytes - oldBytes;
        oldBytes = getTotalRxBytes();
        return (int) finalBytes / refreshTime;
    }

    public CtvitNetSpeedUtils setOnNetSpeedListener(OnNetSpeedListener mListener) {
        this.listener = mListener;
        return this;
    }

    public interface OnNetSpeedListener {
        /**
         * 这个回调是在子线程，如果有UI操作，请自行处理切换到UI线程
         */
        void netSpeed(int netSpeed);
    }

    /**
     * 开启定时任务
     */
    public void startTimerTask() {
        cancelTimerTask();
        oldBytes = getTotalRxBytes();
        timer = new Timer(getClass().getSimpleName());
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.netSpeed(getNetSpeed());
                }
            }
        };
        timer.schedule(timerTask, 1000, refreshTime * 1000);
    }

    /**
     * 开启定时任务
     */
    public void cancelTimerTask() {
        if (timer != null)
            timer.cancel();
    }
}
