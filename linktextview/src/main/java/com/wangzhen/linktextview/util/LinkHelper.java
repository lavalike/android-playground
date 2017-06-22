package com.wangzhen.linktextview.util;

import android.support.annotation.ColorInt;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.wangzhen.linktextview.LinkTextView;

/**
 * 帮助类
 * Created by wangzhen on 2017/6/22.
 */
public class LinkHelper {
    //下划线颜色
    private int mLinkColor;
    private LinkTextView.OnLinkClickListener mLinkListener;

    /**
     * 设置超链接颜色
     *
     * @param colorResId
     */
    public void setLinkColor(@ColorInt int colorResId) {
        mLinkColor = colorResId;
    }

    public void setOnLinkClickListener(LinkTextView.OnLinkClickListener listener) {
        this.mLinkListener = listener;
    }

    /**
     * TextView处理Html中的超链接
     *
     * @param html
     * @return
     */
    public SpannableStringBuilder processHyperlinks(String html) {
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
     * 处理url：超链接颜色、下划线、点击事件
     */
    private class LinkClickSpan extends ClickableSpan {
        private String url = "";

        private LinkClickSpan(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            if (mLinkListener != null) {
                mLinkListener.onClick(url);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(mLinkColor);
            ds.setUnderlineText(false);
        }
    }
}
