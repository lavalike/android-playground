package com.android.exercise.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangzhen on 2017/4/11.
 */

public class AppUtil {

    public static Context mApp;

    public static void init(Context context) {
        mApp = context;
    }

    public static Context getContext() {
        return mApp;
    }

    /**
     * 读取指定Assets目录下文本资源内容
     *
     * @param assetPath
     * @return
     */
    public static String getAssetsText(String assetPath) {
        AssetManager a = getContext().getAssets();
        try {
            StringBuffer buffer = new StringBuffer();
            InputStream is = a.open(assetPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String lineStr;
            while ((lineStr = reader.readLine()) != null) {
                buffer.append(lineStr);
            }
            reader.close();
            is.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
