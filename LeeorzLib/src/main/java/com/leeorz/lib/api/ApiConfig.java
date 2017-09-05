package com.leeorz.lib.api;

/**
 * Created by lee on 17/4/3.
 */

public class ApiConfig {
    public static String HOST = "";
    public static int DEFAULT_TIMEOUT = 60;//60秒超时

    public static void init(String host){
        HOST = host;
    }
}
