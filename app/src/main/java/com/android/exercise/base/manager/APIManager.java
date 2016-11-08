package com.android.exercise.base.manager;

/**
 * API管理类w
 * Created by wangzhen on 16/11/8.
 */

public class APIManager {
    private static boolean isDebug = true;
    private static final String BASE_URL = "http://api.github.com/";
    private static final String BASE_URL_DEBUG = "http://api.github.com/";

    public static String getBaseUrl() {
        return isDebug ? BASE_URL_DEBUG : BASE_URL;
    }

    public static final class endPoint {

    }
}
