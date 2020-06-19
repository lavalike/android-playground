package com.android.exercise.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.ui.widget.nineoldandroids.animation.ValueAnimator;
import com.android.exercise.util.UIUtils;

public class PathMeasureView extends View {

    private Paint paint;
    private int radius;
    private Path path;
    private int mWidth;
    private int mHeight;
    private Bitmap bitmap;
    private Matrix matrix;
    private PathMeasure pathMeasure;
    private float progress;
    private float[] pos;
    private float[] tan;
    private int bitmapWidth;
    private int bitmapHeight;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        path = new Path();
        pathMeasure = new PathMeasure();
        pos = new float[2];
        tan = new float[2];

        radius = 300;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UIUtils.dip2px(getContext(), 2));
        paint.setTextSize(UIUtils.sp2px(getContext(), 15));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow, options);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        matrix = new Matrix();

        startAnim();
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000 * 5);
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFC107"));
        canvas.translate(mWidth / 2, mHeight / 2);
        path.reset();
        path.addCircle(0, 0, radius, Path.Direction.CW);
        pathMeasure.setPath(path, false);

        //方法1
        pathMeasure.getPosTan(pathMeasure.getLength() * progress, pos, tan);
        matrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        matrix.postRotate(degrees, bitmapWidth / 2, bitmapHeight / 2);
        matrix.postTranslate(pos[0] - bitmapWidth / 2, pos[1] - bitmapHeight / 2);

//        //方法2
//        pathMeasure.getMatrix(pathMeasure.getLength() * progress, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
//        matrix.preTranslate(-bitmapWidth / 2, -bitmapHeight / 2);

        canvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
