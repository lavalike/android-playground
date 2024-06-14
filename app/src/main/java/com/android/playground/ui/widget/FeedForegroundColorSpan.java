package com.android.playground.ui.widget;

import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.ColorRes;

import com.android.playground.util.AppUtil;

public class FeedForegroundColorSpan extends ForegroundColorSpan {
    private final int colorRes;

    public FeedForegroundColorSpan(@ColorRes int color) {
        super(AppUtil.getContext().getResources().getColor(color));
        colorRes = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(AppUtil.getContext().getResources().getColor(colorRes));
    }
}
