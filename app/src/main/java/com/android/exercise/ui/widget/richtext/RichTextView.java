package com.android.exercise.ui.widget.richtext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.android.exercise.ui.activity.view.HtmlActivity;
import com.android.exercise.util.DataHelper;
import com.android.exercise.util.IKey;
import com.android.exercise.util.UIUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

/**
 * 显示Html富文本的TextView
 * Created by wangzhen on 2019-12-06.
 */
public class RichTextView extends AppCompatTextView {
    private static final String THREE_DOTS = "...";
    private static final int THREE_DOTS_LENGTH = 0;
    private SpannableStringBuilder spannableStringBuilder;

    private String mHtmlText;
    private static int DEFAULT_WIDTH;
    private static int DEFAULT_HEIGHT;
    private static float DEFAULT_RADIUS;
    private int mDrawableWidth;
    private int mDrawableHeight;
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
        DEFAULT_WIDTH = UIUtils.dip2px(context, 174);
        DEFAULT_HEIGHT = UIUtils.dip2px(context, 98);
        DEFAULT_RADIUS = UIUtils.dip2px(context, 2);

        mDrawableWidth = DEFAULT_WIDTH;
        mDrawableHeight = DEFAULT_HEIGHT;
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
        Executors.newCachedThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
    }

    /**
     * process html text
     */
    private void process() {
        final Spanned spanned;
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
                builder.setSpan(new LinkClickSpan(span.getURL()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            ImageSpan[] imageSpans = builder.getSpans(0, builder.length(), ImageSpan.class);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < imageSpans.length; i++) {
                ImageSpan span = imageSpans[i];
                int start = builder.getSpanStart(span);
                int end = builder.getSpanEnd(span);
                list.add(span.getSource());
                builder.setSpan(new ImageClickSpan(i, list), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        post(new Runnable() {
            @Override
            public void run() {
                setText(builder);
            }
        });
    }

    /**
     * process all img tags
     */
    Html.ImageGetter mImageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {

            Uri uri = Uri.parse(source);
            String w = uri.getQueryParameter("w");
            String h = uri.getQueryParameter("h");
            if (!TextUtils.isEmpty(w) && !TextUtils.isEmpty(h)) {
                try {
                    int width = Integer.parseInt(w);
                    int height = Integer.parseInt(h);
                    if (height != 0) {
                        mDrawableHeight = (int) (mDrawableWidth / (width * 1f / height));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            Drawable drawable = (Drawable) DataHelper.get().getData(source);
            if (drawable == null) {
                //设置默认占位
                drawable = defaultDrawable();
                download(source);
            } else {
                drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            }
            return drawable;
        }
    };

    /**
     * download and cache the drawable
     *
     * @param source img url
     */
    private void download(final String source) {
        post(new Runnable() {
            @Override
            public void run() {
                Glide.with(getContext()).asBitmap().load(source)
                        .apply(new RequestOptions().override(mDrawableWidth / 4, mDrawableHeight / 4))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                                drawable.setCornerRadius(DEFAULT_RADIUS);
                                drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
                                DataHelper.get().put(source, drawable);
                                process();
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                DataHelper.get().put(source, defaultDrawable());
                                process();
                            }
                        });
            }
        });
    }

    /**
     * create default drawable
     *
     * @return drawable
     */
    private Drawable defaultDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(DEFAULT_RADIUS);
        drawable.setColor(Color.parseColor("#e4e4e4"));
        drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        return drawable;
    }

    /**
     * handle image click span
     */
    private static class ImageClickSpan extends ClickableSpan {
        List<String> mList;
        int mIndex;

        public ImageClickSpan(int index, List<String> list) {
            this.mIndex = index;
            this.mList = list;
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (mList == null || mList.isEmpty() || mIndex < 0) {
                return;
            }
            Toast.makeText(widget.getContext(), String.format(Locale.CHINESE, "第%d张图片->%s", mIndex, mList.get(mIndex)), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * handle link click span
     */
    private static class LinkClickSpan extends ClickableSpan {
        private String url;

        public LinkClickSpan(String url) {
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
        if (getLayout().getLineCount() >= getMaxLines() && getMaxLines() > 0) {
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
