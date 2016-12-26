package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.util.DisplayUtil;

/**
 * 引导页动画
 * Created by wangzhen on 2016/12/23.
 */

public class AnimView extends View {

    //宽度
    private int mWidth = 100;
    //高度
    private int mHeight = 150;
    private Paint mPaintStroke;

    public AnimView(Context context) {
        super(context);
        init();
    }

    public AnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWidth = DisplayUtil.dip2px(getContext(), 100);
        mHeight = DisplayUtil.dip2px(getContext(), 150);
        //外边框画笔
        mPaintStroke = new Paint();
        mPaintStroke.setColor(ContextCompat.getColor(getContext(), R.color.grey));
        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0, 0, mWidth, mHeight, mPaintStroke);
        canvas.drawRoundRect(0, 0, mWidth, mHeight, 20,20,mPaintStroke);
    }

    /**
     * 设置宽度
     *
     * @param width dp
     */
    public void setWidth(int width) {
        this.mWidth = DisplayUtil.dip2px(getContext(), width);
        invalidate();
    }

    /**
     * 设置高度
     *
     * @param height dp
     */
    public void setHeight(int height) {
        this.mHeight = DisplayUtil.dip2px(getContext(), height);
        invalidate();
    }
}
