package com.android.exercise.ui.widget.richtext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;

import com.android.exercise.ui.activity.HtmlActivity;
import com.android.exercise.util.IKey;
import com.android.exercise.util.UIUtils;

import java.net.URL;
import java.util.concurrent.Executors;

/**
 * 显示Html富文本的TextView
 * Created by wangzhen on 2019-12-06.
 */
public class RichTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String THREE_DOTS = "...";
    private static final int THREE_DOTS_LENGTH = 0;
    private SpannableStringBuilder spannableStringBuilder;

    private String mHtmlText;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private float mDrawableRadius;
    private boolean mBriefMode;// brief mode

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHighlightColor(ContextCompat.getColor(context, android.R.color.transparent));
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        mDrawableWidth = UIUtils.dip2px(context, 174);
        mDrawableHeight = UIUtils.dip2px(context, 98);
        mDrawableRadius = UIUtils.dip2px(context, 2);
    }

    /**
     * show image in brief mode
     *
     * @param brief brief mode or not
     */
    public void setBriefMode(boolean brief) {
        this.mBriefMode = brief;
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
                Spanned spanned;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    spanned = Html.fromHtml(mHtmlText, Html.FROM_HTML_MODE_COMPACT, mImageGetter, null);
                } else {
                    spanned = Html.fromHtml(mHtmlText, mImageGetter, null);
                }
                final SpannableStringBuilder builder = new SpannableStringBuilder(spanned);
                if (mBriefMode) {
                    //处理超链接
                    URLSpan[] urlSpans = builder.getSpans(0, builder.length(), URLSpan.class);
                    String holder = "[链接:%s]";
                    for (URLSpan span : urlSpans) {
                        int start = builder.getSpanStart(span);
                        int end = builder.getSpanEnd(span);
                        builder.removeSpan(span);
                        builder.replace(start, end, String.format(holder, builder.subSequence(start, end)));
                    }

                    //处理图片
                    ImageSpan[] imageSpans = builder.getSpans(0, builder.length(), ImageSpan.class);
                    holder = "[图片]";
                    for (ImageSpan span : imageSpans) {
                        int start = builder.getSpanStart(span);
                        int end = builder.getSpanEnd(span);
                        builder.removeSpan(span);
                        builder.replace(start, end, holder);
                    }
                } else {
                    URLSpan[] urlSpans = builder.getSpans(0, builder.length(), URLSpan.class);
                    for (URLSpan span : urlSpans) {
                        int start = builder.getSpanStart(span);
                        int end = builder.getSpanEnd(span);
                        builder.removeSpan(span);
                        builder.setSpan(new RichTextUrlSpan(span.getURL()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
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
            try {
                URL url = new URL(source);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), ((BitmapDrawable) Drawable.createFromStream(url.openStream(), "img")).getBitmap());
                roundedBitmapDrawable.setCornerRadius(mDrawableRadius);
                drawable = roundedBitmapDrawable;
            } catch (Exception e) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(mDrawableRadius);
                gradientDrawable.setColor(Color.parseColor("#e4e4e4"));
                drawable = gradientDrawable;
            }
            drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            return drawable;
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
            Intent intent = new Intent(widget.getContext(), HtmlActivity.class);
            intent.putExtra(IKey.HTML_URL, url);
            widget.getContext().startActivity(intent);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getLayout().getLineCount() >= getMaxLines()) {
            CharSequence charSequence = getText();
            int lastCharDown = getLayout().getLineVisibleEnd(getMaxLines() - 1);
            if (lastCharDown >= THREE_DOTS_LENGTH && charSequence.length() > lastCharDown) {
                if (spannableStringBuilder == null) {
                    spannableStringBuilder = new SpannableStringBuilder();
                } else {
                    spannableStringBuilder.clear();
                }
                spannableStringBuilder.append(charSequence.subSequence(0, lastCharDown - THREE_DOTS_LENGTH)).append(THREE_DOTS);
                setText(spannableStringBuilder);
            }
        }
        super.onDraw(canvas);
    }
}
