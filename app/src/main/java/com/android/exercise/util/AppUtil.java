package com.android.exercise.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import com.android.exercise.ui.widget.LinkClickSpan;

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

    /**
     * TextView处理Html中的超链接
     *
     * @param html
     * @return
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
}
