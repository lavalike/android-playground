package com.android.playground.ui.widget.richtext;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 解决ClickableSpan使用系统LinkMovementMethod点击滑动的问题
 * Created by wangzhen on 2019-12-10.
 */
public class CustomLinkMovementMethod extends BaseMovementMethod {
    private static CustomLinkMovementMethod instance;

    public static CustomLinkMovementMethod getInstance() {
        if (instance == null) {
            synchronized (CustomLinkMovementMethod.class) {
                if (instance == null) {
                    instance = new CustomLinkMovementMethod();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    //除了点击事件，我们不要其他东西
                    link[0].onClick(widget);
                }
                return true;
            }
        }
        return true;
    }
}
