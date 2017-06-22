package com.wangzhen.linktextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import com.wangzhen.linktextview.util.LinkHelper;

/**
 * 带超链接的TextView
 * Created by wangzhen on 2017/6/22.
 */
public class LinkTextView extends AppCompatTextView {
    private LinkHelper mLinkHelper;

    public LinkTextView(Context context) {
        this(context, null);
    }

    public LinkTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //启用超链接事件
        setMovementMethod(LinkMovementMethod.getInstance());
        mLinkHelper = new LinkHelper();
        TypedArray typedArray = getContext().obtainStyledAttributes(R.styleable.LinkTextView);
        int linkColor = typedArray.getColor(R.styleable.LinkTextView_linkColor, ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright));
        mLinkHelper.setLinkColor(linkColor);
        typedArray.recycle();
    }

    /**
     * 设置超链接颜色
     *
     * @param colorResId
     */
    public void setLinkColor(@ColorInt int colorResId) {
        mLinkHelper.setLinkColor(colorResId);
    }

    /**
     * 设置文本
     *
     * @param html 内嵌超链接的文本
     */
    public void setText(String html) {
        super.setText(mLinkHelper.processHyperlinks(html));
    }

    public void setOnLinkClickListener(OnLinkClickListener listener) {
        mLinkHelper.setOnLinkClickListener(listener);
    }

    /**
     * 超链接监听器
     */
    public interface OnLinkClickListener {
        void onClick(String url);
    }
}
