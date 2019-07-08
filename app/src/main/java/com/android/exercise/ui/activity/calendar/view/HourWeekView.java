package com.android.exercise.ui.activity.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;


/**
 * HourWeekView 周视图
 * Created by wangzhen on 2019-07-08.
 */
public class HourWeekView extends WeekView {
    private float mRadius;

    public HourWeekView(Context context) {
        super(context);
        mRadius = dipToPx(getContext(), 18);
    }

    /**
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        mSelectedPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x + mItemWidth / 2f, mItemHeight / 2f, mRadius, mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        String day = String.valueOf(calendar.getDay());
        float cx = x + mItemWidth / 2f;
        float cy = mItemHeight / 2f + getTextHeight() / 4f;

        if (isSelected) {
            canvas.drawText(day, cx, cy, mSelectTextPaint);
        } else {
            canvas.drawText(day, cx, cy,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * 获取文字宽度
     *
     * @param text text
     * @return width
     */
    private float getTextWidth(String text) {
        return mSelectedPaint.measureText(text);
    }

    /**
     * 获取文字高度
     *
     * @return height
     */
    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = mSelectTextPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
