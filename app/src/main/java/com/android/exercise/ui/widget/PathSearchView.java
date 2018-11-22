package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.android.exercise.R;
import com.android.exercise.ui.widget.nineoldandroids.animation.AnimatorSet;
import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;

/**
 * 搜索View
 * Created by wangzhen on 2018/11/22.
 */
public class PathSearchView extends View {
    private int minWidth = dip2px(50);
    private int minHeight = dip2px(50);
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int radius;//外圆半径
    private Paint paint;
    private Path pathInner;
    private Path pathHandle;
    private PathMeasure pathMeasure;
    private float[] pos;
    private Path pathDst;
    private float innerProgress;//放大镜圆弧绘制进度
    private float handleProgress;//放大手柄绘制进度
    private AnimatorSet innerAnimator;
    private ValueAnimator outerAnimator;
    private int startAngle = -90;
    private int minAngle = -90;
    private int sweepAngle = 0;
    private int curAngle = 0;
    private RectF rectOuter;//外圆所有的RectF
    private State currentState = State.DONE;
    private int color;
    private float strokeWidth;

    public PathSearchView(Context context) {
        this(context, null);
    }

    public PathSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
        setState(currentState);
    }

    /**
     * Attrs
     *
     * @param attrs attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PathSearchView);
        radius = (int) typedArray.getDimension(R.styleable.PathSearchView_psv_radius, dip2px(20));
        color = typedArray.getColor(R.styleable.PathSearchView_psv_border_color, Color.WHITE);
        strokeWidth = typedArray.getDimension(R.styleable.PathSearchView_psv_border_width, dip2px(2));
        typedArray.recycle();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        pathInner = new Path();
        pathHandle = new Path();
        pathDst = new Path();

        pos = new float[2];

        pathMeasure = new PathMeasure();

        rectOuter = new RectF(-radius, -radius, radius, radius);
        pathInner.addArc(new RectF(-radius / 2, -radius / 2, radius / 2, radius / 2), 45, 359.9F);

        pathMeasure.setPath(pathInner, false);
        pathMeasure.getPosTan(pathMeasure.getLength(), pos, null);
        pathHandle.moveTo(pos[0], pos[1]);
        pathHandle.lineTo(radius * 2 / 3, radius * 2 / 3);

        initAnimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 初始化Animator
     */
    private void initAnimator() {
        ValueAnimator innerCircleAnimator = ValueAnimator.ofFloat(0, 1);
        innerCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                innerProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        innerCircleAnimator.setDuration(800);

        ValueAnimator innerHandleAnimator = ValueAnimator.ofFloat(0, 1);
        innerHandleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                handleProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        innerHandleAnimator.setDuration(400);

        innerAnimator = new AnimatorSet();
        innerAnimator.play(innerCircleAnimator).before(innerHandleAnimator);

        outerAnimator = ValueAnimator.ofFloat(0, 1);
        outerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        outerAnimator.setRepeatCount(Animation.INFINITE);
        outerAnimator.setRepeatMode(Animation.RESTART);
        outerAnimator.setDuration(2000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec spec
     * @return height
     */
    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(minHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = minHeight;
                break;
        }
        return MeasureSpec.makeMeasureSpec(height, heightMode);
    }

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec spec
     * @return width
     */
    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(minWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = minWidth;
                break;
        }
        return MeasureSpec.makeMeasureSpec(width, widthMode);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        if (currentState == State.DONE) {
            drawInner(canvas);
        }
        if (currentState == State.SEARCHING) {
            drawOuter(canvas);
        }
    }

    /**
     * 绘制外部弧形
     *
     * @param canvas canvas
     */
    private void drawOuter(Canvas canvas) {
        if (startAngle == minAngle) {
            sweepAngle += 6;
        }
        if (sweepAngle >= 300 || startAngle > minAngle) {
            startAngle += 6;
            if (sweepAngle > 20) {
                sweepAngle -= 6;
            }
        }
        if (startAngle > minAngle + 300) {
            startAngle %= 360;
            minAngle = startAngle;
            sweepAngle = 20;
        }
        canvas.rotate(curAngle += 4, 0, 0);
        canvas.drawArc(rectOuter, startAngle, sweepAngle, false, paint);
    }

    /**
     * 绘制内部放大镜
     *
     * @param canvas canvas
     */
    private void drawInner(Canvas canvas) {
        pathMeasure.setPath(pathInner, false);
        pathMeasure.getSegment(0, pathMeasure.getLength() * innerProgress, pathDst, true);
        canvas.drawPath(pathDst, paint);
        if (innerProgress == 1) {
            pathMeasure.nextContour();
            pathMeasure.setPath(pathHandle, false);
            pathMeasure.getSegment(0, pathMeasure.getLength() * handleProgress, pathDst, true);
            canvas.drawPath(pathDst, paint);
        }
    }

    /**
     * 设置状态并更新
     *
     * @param state 状态
     */
    public void setState(State state) {
        currentState = state;
        reset();
        if (currentState == State.DONE) {
            innerAnimator.start();
        }
        if (currentState == State.SEARCHING) {
            outerAnimator.start();
        }
    }

    /**
     * 重置所有状态
     */
    private void reset() {
        pathDst.reset();
        innerAnimator.removeAllListeners();
        outerAnimator.removeAllListeners();
    }

    public enum State {
        DONE,//停止状态
        SEARCHING //搜索状态
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
