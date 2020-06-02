package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.exercise.R;

public class StateButton extends LinearLayout {

    private int DEFAULT_ICON_WIDTH = dip2px(20);
    private int DEFAULT_ICON_HEIGHT = dip2px(20);
    private int DEFAULT_ICON_STROKE_WIDTH = dip2px(1);
    private static final int DEFAULT_TEXT_SIZE = 15;
    private StateProgress stateProgress;
    private TextView textView;
    private String textNormal;
    private String textLoading;
    private String textSuccess;
    private float textSize;
    private int textColor;
    private int iconWidth;
    private int iconHeight;
    private float iconTextPadding;
    private int iconColor;
    private float iconStrokeWidth;
    private ColorStateList textColorStateList;

    public StateButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        init();
    }

    public StateButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StateButton);
        textNormal = typedArray.getString(R.styleable.StateButton_sb_text_normal);
        textLoading = typedArray.getString(R.styleable.StateButton_sb_text_loading);
        textSuccess = typedArray.getString(R.styleable.StateButton_sb_text_success);
        textSize = typedArray.getInteger(R.styleable.StateButton_sb_text_size, DEFAULT_TEXT_SIZE);
        textColor = typedArray.getColor(R.styleable.StateButton_sb_text_color, Color.BLACK);
        textColorStateList = typedArray.getColorStateList(R.styleable.StateButton_sb_text_color_state_list);
        iconWidth = typedArray.getDimensionPixelSize(R.styleable.StateButton_sb_icon_width, DEFAULT_ICON_WIDTH);
        iconHeight = typedArray.getDimensionPixelSize(R.styleable.StateButton_sb_icon_width, DEFAULT_ICON_HEIGHT);
        iconTextPadding = typedArray.getDimensionPixelSize(R.styleable.StateButton_sb_text_icon_padding, 0);
        iconColor = typedArray.getColor(R.styleable.StateButton_sb_icon_color, Color.BLACK);
        iconStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.StateButton_sb_icon_stroke_width, DEFAULT_ICON_STROKE_WIDTH);
        if (TextUtils.isEmpty(textNormal)) textNormal = "";
        if (TextUtils.isEmpty(textLoading)) textLoading = "";
        if (TextUtils.isEmpty(textSuccess)) textSuccess = "";
        typedArray.recycle();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        stateProgress = new StateProgress(getContext());
        stateProgress.setPaintColor(iconColor);
        stateProgress.setStrokeWidth(iconStrokeWidth);
        stateProgress.setVisibility(View.GONE);

        textView = new TextView(getContext());
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        if (textColorStateList != null)
            textView.setTextColor(textColorStateList);
        textView.setText(textNormal);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iconWidth, iconHeight);
        layoutParams.setMargins(0, 0, (int) iconTextPadding, 0);
        addView(stateProgress, layoutParams);
        addView(textView);
    }

    public void startLoading() {
        setClickable(false);
        stateProgress.setVisibility(View.VISIBLE);
        stateProgress.startLoading();
        textView.setText(textLoading);
    }

    public void reset() {
        setClickable(true);
        stateProgress.setVisibility(View.GONE);
        textView.setText(textNormal);
    }

    public void success() {
        setClickable(false);
        stateProgress.setVisibility(View.VISIBLE);
        stateProgress.success();
        textView.setText(textSuccess);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (textView != null)
            textView.setEnabled(enabled);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}