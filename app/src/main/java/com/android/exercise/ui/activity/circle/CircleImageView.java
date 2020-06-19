package com.android.exercise.ui.activity.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.android.exercise.R;

/**
 * circle or round corner image view
 * Created by wangzhen on 2020/6/19.
 */
public class CircleImageView extends AppCompatImageView {

    private Paint mPaint;
    private RectF mRect;
    private Path mPath;
    private Path mXorPath;

    private boolean mOval;
    private float[] radii = new float[8];

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mOval = typedArray.getBoolean(R.styleable.CircleImageView_oval, false);
        if (!mOval) {
            float radiusTopLeft;
            float radiusTopRight;
            float radiusBottomLeft;
            float radiusBottomRight;

            float radius = typedArray.getDimension(R.styleable.CircleImageView_radius, 0);
            if (radius > 0) {
                radiusTopLeft = radiusTopRight = radiusBottomLeft = radiusBottomRight = radius;
            } else {
                radiusTopLeft = typedArray.getDimension(R.styleable.CircleImageView_radius_top_left, 0);
                radiusTopRight = typedArray.getDimension(R.styleable.CircleImageView_radius_top_right, 0);
                radiusBottomLeft = typedArray.getDimension(R.styleable.CircleImageView_radius_bottom_left, 0);
                radiusBottomRight = typedArray.getDimension(R.styleable.CircleImageView_radius_bottom_right, 0);
            }
            setRadii(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight);
        }
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? new PorterDuffXfermode(PorterDuff.Mode.CLEAR) : new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mPath = new Path();
        mRect = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        super.onDraw(canvas);
        if (mOval) {
            drawOval(canvas);
        } else {
            drawRoundCorner(canvas);
        }
        canvas.restoreToCount(layerCount);
    }

    /**
     * draw round rect path with corners
     *
     * @param canvas canvas
     */
    private void drawRoundCorner(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(mRect, radii, Path.Direction.CW);
        canvas.drawPath(xor(mPath), mPaint);
    }

    /**
     * draw oval path
     *
     * @param canvas canvas
     */
    private void drawOval(Canvas canvas) {
        mPath.reset();
        mPath.addOval(mRect, Path.Direction.CW);
        canvas.drawPath(xor(mPath), mPaint);
    }

    /**
     * compat high android versions
     *
     * @param path path
     * @return path
     */
    private Path xor(Path path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mXorPath == null) {
                mXorPath = new Path();
            } else {
                mXorPath.reset();
            }
            mXorPath.addRect(mRect, Path.Direction.CW);
            path.op(mXorPath, Path.Op.XOR);
        }
        return path;
    }

    /**
     * set oval and refresh
     *
     * @param oval oval
     */
    public void setOval(boolean oval) {
        this.mOval = oval;
        invalidate();
    }

    /**
     * set corners and refresh
     *
     * @param topLeft     topLeft
     * @param topRight    topRight
     * @param bottomLeft  bottomLeft
     * @param bottomRight bottomRight
     */
    public void setCorner(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        this.mOval = false;
        setRadii(topLeft, topRight, bottomLeft, bottomRight);
        invalidate();
    }

    /**
     * set radii values
     *
     * @param topLeft     topLeft
     * @param topRight    topRight
     * @param bottomLeft  bottomLeft
     * @param bottomRight bottomRight
     */
    private void setRadii(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        radii[0] = radii[1] = topLeft;
        radii[2] = radii[3] = topRight;
        radii[4] = radii[5] = bottomRight;
        radii[6] = radii[7] = bottomLeft;
    }

}
