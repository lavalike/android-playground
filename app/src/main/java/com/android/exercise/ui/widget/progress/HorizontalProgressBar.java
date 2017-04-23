package com.android.exercise.ui.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.android.exercise.R;

/**
 * 水平进度条
 * Created by wangzhen on 2017/4/22.
 */
public class HorizontalProgressBar extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_UNREACH_COLOR = 0xFFD3D6DA;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;
    private static final int DEFAULT_REACH_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_REACH_HEIGHT = 2;
    private static final int DEFAULT_TEXT_OFFSET = 10;

    protected int mTextSize = dp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnReachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint;
    //实际宽度
    protected int mRealWidth;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributeSet(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean noNeedUnReach = false;
        String text = getProgress() + "%";
        int textWidth = (int) mPaint.measureText(text);
        float radio = getProgress() * 1.0f / getMax();
        float progressX = radio * mRealWidth;
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnReach = true;
        }
        float endX = progressX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        //绘制文本
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        canvas.drawText(text, progressX, y, mPaint);

        //绘制unReachBar
        if (!noNeedUnReach) {
            float start = progressX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }
        canvas.restore();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            int textHeight = (int) Math.abs(mPaint.descent() - mPaint.ascent());
            result = getPaddingTop()
                    + getPaddingBottom()
                    + Math.max(Math.max(mReachHeight, mUnReachHeight), textHeight);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
        mTextSize = dp2px((int) typedArray.getDimension(R.styleable.HorizontalProgressBar_progress_text_size, DEFAULT_TEXT_SIZE));
        mTextColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_text_color, mTextColor);
        mUnReachColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_unreach_color, mUnReachColor);
        mUnReachHeight = dp2px((int) typedArray.getDimension(R.styleable.HorizontalProgressBar_progress_unreach_height, DEFAULT_UNREACH_HEIGHT));
        mReachColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_reach_color, mReachColor);
        mReachHeight = dp2px((int) typedArray.getDimension(R.styleable.HorizontalProgressBar_progress_reach_height, DEFAULT_REACH_HEIGHT));
        mTextOffset = dp2px((int) typedArray.getDimension(R.styleable.HorizontalProgressBar_progress_text_offset, DEFAULT_TEXT_OFFSET));
        typedArray.recycle();
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
