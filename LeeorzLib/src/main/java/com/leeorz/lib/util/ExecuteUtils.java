package com.leeorz.lib.util;

import android.util.Log;

import com.leeorz.lib.app.AppConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc: 运行时间分析Util
 * Created by 庞承晖
 * Date: 2015/9/1.
 * Time: 20:01
 */
public class ExecuteUtils {

    private static String TAG = ExecuteUtils.class.getSimpleName();
    private static boolean flag = AppConfig.DEBUG;

    private static double currentTime = 0L;
    private static Map<String, Double> mTimeMap = new HashMap<>();

    /**
     * 开始时间
     */
    public static void start() {
        if (!flag) return;
        currentTime = System.nanoTime()/1000000.0;
    }

    public static void startTag(String tag) {
        if (!flag) return;
        mTimeMap.put(tag , System.nanoTime()/1000000.0);
    }

    public static void endTag(String tag, int name) {
        if (!flag) return;
        double end = System.nanoTime()/1000000.0;
        double delay = end - mTimeMap.get(tag);
        Log.e(TAG, String.format("%s 代码段执行了------->%4f毫秒, %s", tag, delay, name));
    }

    /**
     * 结束时间
     */
    public static void end() {
        end("");
    }

    /**
     * 结束时间
     *
     * @param tag
     */
    public static void end(String tag) {
        if (!flag) return;
        double end = System.nanoTime()/1000000;
        double delay = end - currentTime;
        currentTime = end;
        Log.e(TAG, String.format("%s 代码段执行了------->%4f毫秒", tag, delay));
    }

}
