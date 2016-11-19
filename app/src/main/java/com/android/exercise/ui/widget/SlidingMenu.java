package com.android.exercise.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.android.exercise.R;
import com.android.exercise.ui.widget.nineoldandroids.view.ViewHelper;

/**
 * 利用HorizontalScrollView扩展的SlidingMenu
 * Created by wangzhen on 2016/11/19.
 */

public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    //屏幕宽度
    private int mScreenWidth;
    //菜单宽度
    private int mMenuWidth;
    //菜单展开时与屏幕右侧的边距
    private int mMenuRightPadding = 50;
    //只调用一次onMeasure
    private boolean onceMeasure = false;
    private boolean onceLayout = false;
    //菜单是否开启 默认false
    private boolean isOpen;


    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 未使用自定义属性时调用
     *
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 使用自定义属性时调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;

        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        mMenuRightPadding = typedArray.getDimensionPixelSize(R.styleable.SlidingMenu_menuRightPadding, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, context.getResources().getDisplayMetrics()));
        typedArray.recycle();
    }

    /**
     * 设置子View的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!onceMeasure) {
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            //设置菜单宽度 屏幕宽度-菜单与屏幕右边距
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            //设置内容区域宽度为屏幕宽度
            mContent.getLayoutParams().width = mScreenWidth;
            //设置SlidingMenu宽度为 菜单宽度+内容区域宽度
            mWrapper.getLayoutParams().width = mScreenWidth + mMenu.getLayoutParams().width;
            onceMeasure = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量，将menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!onceLayout) {
            this.scrollTo(mMenuWidth, 0);
            onceLayout = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        //一、实现抽屉式菜单
//        //属性动画改变菜单的TranslationX，跟随滑动而滑动，呈现菜单静止的效果
//        ViewHelper.setTranslationX(mMenu, l);

        //二、抽屉式带缩放菜单
        float scale = l * 1.0f / mMenuWidth;
        //内容缩放比例
        float contentScale = 0.7f + 0.3f * scale;
        //菜单缩放比例
        float menuScale = 1.0f - scale * 0.3f;
        //菜单透明度变化
        float menuAlpha = 0.6f + 0.4f * (1 - scale);

        //菜单缩放+透明度变化
        ViewHelper.setTranslationX(mMenu, l * 0.7f);//乘以0.7，使菜单有轻微位移效果
        ViewHelper.setScaleX(mMenu, menuScale);
        ViewHelper.setScaleY(mMenu, menuScale);
        ViewHelper.setAlpha(mMenu, menuAlpha);

        //缩放内容区域
        //设置Content缩放的中心点，由内容区域的中心，改为左侧中心
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, contentScale);
        ViewHelper.setScaleY(mContent, contentScale);

    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 展开菜单
     */
    public void openMenu() {
        if (!isOpen) {
            this.smoothScrollTo(0, 0);
            isOpen = true;
        }
    }

    /**
     * 自动判断开/合菜单
     */
    public void toggleMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     * 获得菜单开关状态
     *
     * @return
     */
    public boolean isOpen() {
        return isOpen;
    }
}
