package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.android.exercise.util.UIUtils;

import java.net.URL;
import java.util.concurrent.Executors;

/**
 * 显示Html富文本的TextView
 * Created by wangzhen on 2019-12-06.
 */
public class RichTextView extends android.support.v7.widget.AppCompatTextView {
    private String mHtmlText;

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * set rich text
     *
     * @param html html text
     */

    public void setRichText(String html) {
        this.mHtmlText = html;
        process();
    }

    /**
     * process html text
     */
    private void process() {
        Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                Spanned spanned = Html.fromHtml(mHtmlText, mImageGetter, null);
                SpannableStringBuilder builder = new SpannableStringBuilder(spanned);
                URLSpan[] urlSpans = builder.getSpans(0, builder.length(), URLSpan.class);
                //移除默认点击事件
                for (URLSpan span : urlSpans) {
                    builder.removeSpan(span);
                }
                //添加点击事件
                for (URLSpan span : urlSpans) {
                    builder.setSpan(new RichTextUrlSpan(span.getURL()), spanned.getSpanStart(span), spanned.getSpanEnd(span), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        setText(builder);
                    }
                });
            }
        });
    }

    /**
     * process all img tags
     */
    Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "img");
            } catch (Exception e) {
                return null;
            }
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), ((BitmapDrawable) drawable).getBitmap());
            roundedBitmapDrawable.setBounds(0, 0, UIUtils.dip2px(getContext(), 174), UIUtils.dip2px(getContext(), 98));
            roundedBitmapDrawable.setCornerRadius(UIUtils.dip2px(getContext(), 2));
            return roundedBitmapDrawable;
        }
    };

    /**
     * handle urls
     */
    private static class RichTextUrlSpan extends ClickableSpan {
        private String url;

        public RichTextUrlSpan(String url) {
            this.url = url;
        }

        @Override
        public void onClick(@NonNull View widget) {
            Toast.makeText(widget.getContext(), url, Toast.LENGTH_SHORT).show();
        }
    }
}
