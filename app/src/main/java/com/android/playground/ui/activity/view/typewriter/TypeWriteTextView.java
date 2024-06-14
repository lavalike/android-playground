package com.android.playground.ui.activity.view.typewriter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * TypeWriteTextView
 *
 * @author: zhen51.wang
 * @date: 2022/11/14/014
 */
public class TypeWriteTextView extends AppCompatTextView {
    private static final long TYPE_TIME_INTERVAL = 500;
    private static final String KEY_TEXT = "KEY_TEXT";
    private static final String DEFAULT_DATA = "DEFAULT_DATA";

    private OnTypeListener mTypeListener;
    private ValueAnimator mAnimatorTypeText;
    private int mTextLength;
    private String mText;

    public TypeWriteTextView(Context context) {
        this(context, null);
    }

    public TypeWriteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeWriteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        release();
        mAnimatorTypeText = new ValueAnimator();
        mAnimatorTypeText.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            //value的值会重复所以要加判断，避免文字重复打印
            if (getText().length() - 1 < value) {
                if (value == 0) {
                    if (mTypeListener != null) {
                        mTypeListener.onTypeStart();
                    }
                }
                append(mText.substring(value, value + 1));
            }
            if (value >= mTextLength - 1) {
                if (mTypeListener != null) {
                    mTypeListener.onTypeStop();
                }
                release();
            }
        });

    }

    public void setTypeListener(OnTypeListener typeListener) {
        mTypeListener = typeListener;
    }

    public void bindText(String text) {
        setText("");
        initView();
        mTextLength = text.length();
        mText = text;
        mAnimatorTypeText.setIntValues(0, mTextLength - 1);
        mAnimatorTypeText.setDuration(mTextLength * TYPE_TIME_INTERVAL);
        mAnimatorTypeText.start();

    }

    public void release() {
        if (mAnimatorTypeText != null && mAnimatorTypeText.isRunning()) {
            mAnimatorTypeText.cancel();
            mAnimatorTypeText = null;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        release();
        Bundle bundle = new Bundle();
        Parcelable superData = super.onSaveInstanceState();
        bundle.putParcelable(DEFAULT_DATA, superData);
        bundle.putString(KEY_TEXT, mText);
        setText(mText);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        mText = bundle.getString(KEY_TEXT);
        setText(mText);
        Parcelable superData = bundle.getParcelable(DEFAULT_DATA);
        super.onRestoreInstanceState(superData);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }

    public interface OnTypeListener {
        void onTypeStart();

        void onTypeStop();
    }
}
