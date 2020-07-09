package com.android.exercise.ui.activity.memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

import androidx.appcompat.widget.AppCompatImageView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * LargeImageView
 * 加载巨图
 * Created by wangzhen on 2020/7/9.
 */
public class LargeImageView extends AppCompatImageView implements GestureDetector.OnGestureListener {

    private int mWidth;
    private int mHeight;
    private BitmapRegionDecoder mDecoder;
    private GestureDetector mGestureDetector;
    private Rect mRect = new Rect();
    private BitmapFactory.Options mOptions;
    private Bitmap mBitmap;
    private Scroller mScroller;

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOptions = new BitmapFactory.Options();
        mOptions.inMutable = true;
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        mScroller = new Scroller(context);
    }

    public void setInputStream(@NotNull InputStream stream) {
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, mOptions);
        mWidth = mOptions.outWidth;
        mHeight = mOptions.outHeight;
        mOptions.inJustDecodeBounds = false;
        try {
            mDecoder = BitmapRegionDecoder.newInstance(stream, false);
            mGestureDetector = new GestureDetector(getContext(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRect.left = mWidth / 2 - width / 2;
        mRect.top = mHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mOptions.inBitmap = mBitmap;
        mBitmap = mDecoder.decodeRegion(mRect, mOptions);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //如果图片正在飞速滑动，停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset((int) distanceX, (int) distanceY);
        if (mRect.left < 0) {
            mRect.left = 0;
            mRect.right = getWidth();
        }
        if (mRect.right > mWidth) {
            mRect.right = mWidth;
            mRect.left = mWidth - getWidth();
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = getHeight();
        }
        if (mRect.bottom > mHeight) {
            mRect.bottom = mHeight;
            mRect.top = mHeight - getHeight();
        }
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mRect.left, mRect.top, -(int) velocityX, -(int) velocityY, 0, mWidth, 0, mHeight);
        return false;
    }

    @Override
    public void computeScroll() {
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            if (mRect.top + getHeight() < mHeight) {
                mRect.top = mScroller.getCurrY();
                mRect.bottom = mRect.top + getHeight();
            }
            if (mRect.bottom > mHeight) {
                mRect.bottom = mHeight;
                mRect.top = mRect.bottom - getHeight();
            }
            if (mRect.left + getWidth() < mWidth) {
                mRect.left = mScroller.getCurrX();
                mRect.right = mRect.left + getWidth();
            }
            if (mRect.right > mWidth) {
                mRect.right = mWidth;
                mRect.left = mWidth - getWidth();
            }
            invalidate();
        }
    }
}
