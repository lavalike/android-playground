package com.android.playground.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.playground.util.UIUtils;

public class PathView extends View {

    private Path path1;
    private Path path2;
    private Path pathOpResult;
    private Paint paint;
    private int radius;
    private RectF rectBounds;
    private Paint paintRect;
    private int rectBorderWidth;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path1 = new Path();
        path2 = new Path();
        pathOpResult = new Path();
        rectBounds = new RectF();

        radius = 100;
        rectBorderWidth = UIUtils.dip2px(getContext(), 2);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(UIUtils.sp2px(getContext(), 15));

        paintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setStrokeWidth(rectBorderWidth);
        paintRect.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFC107"));
        path1.addCircle(200, 200, radius, Path.Direction.CW);
        path2.addCircle(350, 200, radius, Path.Direction.CW);
        canvas.drawText("DIFFERENCE", 500, 200, paint);
        pathOpResult.op(path1, path2, Path.Op.DIFFERENCE);
        canvas.drawPath(pathOpResult, paint);

        canvas.translate(0, 250);
        pathOpResult.op(path1, path2, Path.Op.REVERSE_DIFFERENCE);
        canvas.drawText("REVERSE_DIFFERENCE", 500, 200, paint);
        canvas.drawPath(pathOpResult, paint);

        canvas.translate(0, 250);
        pathOpResult.op(path1, path2, Path.Op.INTERSECT);
        canvas.drawText("INTERSECT", 500, 200, paint);
        canvas.drawPath(pathOpResult, paint);

        canvas.translate(0, 250);
        pathOpResult.op(path1, path2, Path.Op.UNION);
        canvas.drawText("UNION", 500, 200, paint);
        canvas.drawPath(pathOpResult, paint);

        canvas.translate(0, 250);
        pathOpResult.op(path1, path2, Path.Op.XOR);
        canvas.drawText("XOR", 500, 200, paint);
        canvas.drawPath(pathOpResult, paint);

        canvas.translate(0, 250);
        pathOpResult.op(path1, path2, Path.Op.XOR);
        pathOpResult.computeBounds(rectBounds, true);
        rectBounds.set(
                rectBounds.left - rectBorderWidth / 2,
                rectBounds.top - rectBorderWidth / 2,
                rectBounds.right + rectBorderWidth / 2,
                rectBounds.bottom + rectBorderWidth / 2
        );
        canvas.drawText("Path.computeBounds()", 500, 200, paint);
        canvas.drawPath(pathOpResult, paint);
        canvas.drawRect(rectBounds, paintRect);
    }
}
