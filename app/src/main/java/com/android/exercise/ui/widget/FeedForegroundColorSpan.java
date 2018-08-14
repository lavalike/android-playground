package com.android.exercise.ui.widget;

import android.support.annotation.ColorRes;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

import com.android.exercise.util.AppUtil;

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
