package com.android.playground.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.android.playground.R;

/**
 * 密码输入框
 * Created by wangzhen on 2017/8/15.
 */
public class PasswordView extends AppCompatEditText {
    private static final String TAG = PasswordView.class.getName();
    private int mWidth;//宽度
    private int mHeight;//高度
    private int maxCount = 6;//默认长度
    private RectF mRectF = new RectF();//边框Rectf
    private Paint mPaintBorder;
    private int mBorderCorner = dip2px(2);//边框圆角
    private int mColorBorder = Color.GRAY;//边框颜色
    private int mBorderWidth = dip2px(1);//边框宽度
    private Paint mPaintDivider;
    private int mDividerWidth = dip2px(1);//分隔线宽度
    private int mColorDivider = Color.GRAY;//分隔线颜色
    private int mDividerStartX;
    private static int textLength = 0;//文字长度
    private float mDotStartX;
    private float mDotStartY;
    private int mDotRadius = dip2px(4);//圆点半径
    private Paint mPaintDot;//圆点画笔
    private int mColorDot = Color.GRAY;//圆点颜色

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.editTextStyle);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaints();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        mPaintBorder = createPaint(mBorderWidth, Paint.Style.STROKE, mColorBorder);
        mPaintDivider = createPaint(mDividerWidth, Paint.Style.FILL, mColorDivider);
        mPaintDot = createPaint(5, Paint.Style.FILL, mColorDot);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordView);
        maxCount = typedArray.getInt(R.styleable.PasswordView_pv_max_count, maxCount);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.PasswordView_pv_border_width, mBorderWidth);
        mBorderCorner = typedArray.getDimensionPixelSize(R.styleable.PasswordView_pv_border_corner, mBorderCorner);
        mColorBorder = typedArray.getColor(R.styleable.PasswordView_pv_border_color, mColorBorder);
        mDividerWidth = typedArray.getDimensionPixelSize(R.styleable.PasswordView_pv_divider_width, mDividerWidth);
        mColorDivider = typedArray.getColor(R.styleable.PasswordView_pv_divider_color, mColorDivider);
        mDotRadius = typedArray.getDimensionPixelSize(R.styleable.PasswordView_pv_dot_radius, mDotRadius);
        mColorDot = typedArray.getColor(R.styleable.PasswordView_pv_dot_color, mColorDot);
        typedArray.recycle();
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    /**
     * 创建画笔
     *
     * @param strokeWidth 边框宽度
     * @param style       样式
     * @param color       颜色
     * @return 画笔
     */
    private Paint createPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mDividerStartX = mWidth / maxCount;
        mDotStartX = mWidth / maxCount / 2;
        mDotStartY = mHeight / 2;
        mRectF.set(mBorderWidth / 2, mBorderWidth / 2, mWidth - mBorderWidth / 2, mHeight - mBorderWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawDivider(canvas);
        drawDot(canvas);
    }

    /**
     * 绘制圆点
     *
     * @param canvas
     */
    private void drawDot(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(mDotStartX + i * 2 * mDotStartX, mDotStartY, mDotRadius, mPaintDot);
        }
    }

    /**
     * 绘制分隔线
     *
     * @param canvas
     */
    private void drawDivider(Canvas canvas) {
        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * mDividerStartX + mBorderWidth / 2, 0, (i + 1) * mDividerStartX, mHeight - mBorderWidth / 2, mPaintDivider);
        }
    }

    /**
     * 绘制边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        canvas.drawRoundRect(mRectF, dip2px(mBorderCorner), dip2px(mBorderCorner), mPaintBorder);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.toString().length();
        invalidate();
    }

    /**
     * 获取密码
     *
     * @return pwd
     */
    public String getPassword() {
        return getText().toString().trim();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
