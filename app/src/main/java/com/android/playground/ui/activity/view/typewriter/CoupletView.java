package com.android.playground.ui.activity.view.typewriter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * CoupletView
 *
 * @author: zhen51.wang
 * @date: 2022/11/14/014
 */
public class CoupletView extends LinearLayout {
    public long delay = 200;
    private int textSize = 25;
    private final CoupletHandler handler = new CoupletHandler();
    Callback callback;

    public CoupletView(Context context) {
        this(context, null);
    }

    public CoupletView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoupletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }

    public CoupletView textSize(int size) {
        this.textSize = size;
        return this;
    }

    public CoupletView delay(long millis) {
        this.delay = millis;
        return this;
    }

    public CoupletView callback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public void update(@NotNull String txt) {
        removeAllViews();
        cancelMessages();
        handler.render(txt.split(""));
    }

    @SuppressLint("HandlerLeak")
    private class CoupletHandler extends Handler {
        String[] items;
        int index = 0;

        public CoupletHandler() {
            super(Looper.getMainLooper());
        }

        public void render(String[] data) {
            this.items = data;
            this.index = 0;
            promoteAndTry();
        }

        private void promoteAndTry() {
            if (index < this.items.length) {
                sendEmptyMessageDelayed(0, delay);
            } else {
                if (callback != null) {
                    callback.onFinish();
                }
            }
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            addView(newChild(this.items[index]));
            index++;
            promoteAndTry();
        }
    }

    private TextView newChild(String word) {
        TextView child = new TextView(getContext());
        child.setPadding(20, 20, 20, 20);
        child.setText(word);
        child.setTextSize(textSize);
        child.setTextColor(Color.BLACK);
        child.setTypeface(Typeface.MONOSPACE);
        child.getPaint().setFakeBoldText(true);
        return child;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelMessages();
    }

    private void cancelMessages() {
        handler.removeCallbacksAndMessages(null);
    }

    interface Callback {
        void onFinish();
    }
}
