package com.android.playground.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import com.android.playground.ui.widget.LinkClickSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangzhen on 2017/4/11.
 */

public class AppUtil {

    @SuppressLint("StaticFieldLeak")
    public static Context mApp;

    public static void init(Context context) {
        mApp = context.getApplicationContext();
    }

    public static Context getContext() {
        return mApp;
    }

    /**
     * 读取指定Assets目录下文本资源内容
     */
    public static String getAssetsText(String assetPath) {
        AssetManager a = getContext().getAssets();
        try {
            StringBuilder builder = new StringBuilder();
            InputStream is = a.open(assetPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String lineStr;
            while ((lineStr = reader.readLine()) != null) {
                builder.append(lineStr);
            }
            reader.close();
            is.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TextView处理Html中的超链接
     */
    public static SpannableStringBuilder processHyperlinks(String html) {
        Spannable spannable = (Spannable) Html.fromHtml(html);
        URLSpan[] urls = spannable.getSpans(0, spannable.length(), URLSpan.class);
        SpannableStringBuilder builder = new SpannableStringBuilder(spannable);
        builder.clearSpans();
        LinkClickSpan linkSpan;
        for (URLSpan url : urls) {
            linkSpan = new LinkClickSpan(url.getURL());
            builder.setSpan(linkSpan, spannable.getSpanStart(url), spannable.getSpanEnd(url), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 通过Context获取真正的Activity
     *
     * @param ctx context
     * @return activity
     */
    public static Activity getRealActivity(Context ctx) {
        while (ctx instanceof ContextWrapper) {
            if (ctx instanceof Activity) {
                return (Activity) ctx;
            }
            ctx = ((ContextWrapper) ctx).getBaseContext();
        }
        return null;
    }

    /**
     * get app name
     *
     * @return name
     */
    public static String getAppName() {
        return mApp.getApplicationInfo().loadLabel(mApp.getPackageManager()).toString();
    }
}
