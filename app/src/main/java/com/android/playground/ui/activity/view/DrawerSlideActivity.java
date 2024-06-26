package com.android.playground.ui.activity.view;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.drawerlayout.widget.DrawerLayout;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.databinding.ActivityDrawerSlideBinding;
import com.android.playground.util.T;
import com.android.playground.util.UIUtils;

import java.util.ArrayList;

public class DrawerSlideActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private long mLastClickTime = 0;
    private int mClickTimes = 0;
    private int TOTAL_CLICK = 7;

    private GestureDetector mGestureDetector;
    private int minDistance = 300;//最小滑动距离
    private int FLAG_HORIZENTAL = 0;//横向
    private int FLAG_VERTICAL = 1;//纵向
    private ArrayList<Integer> mGestureTemplate;
    private ArrayList<Integer> mGestureList;

    private ActivityDrawerSlideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityDrawerSlideBinding.inflate(getLayoutInflater())).getRoot());
        binding.btnOpenHide.setOnClickListener(v -> openHide());
        initDrawerLayout();
        initGesture();
    }

    private void initDrawerLayout() {
        //去除阴影
        binding.drawerSlide.setScrimColor(getResources().getColor(android.R.color.transparent));
        binding.drawerSlide.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                int width = drawerView.getWidth();
                int left = (int) (slideOffset * width + 0.5f);
                int right = left + binding.drawerContent.getWidth();
                binding.drawerContent.layout(left, binding.drawerContent.getTop(), right, binding.drawerContent.getBottom());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        View menuView = getLayoutInflater().inflate(R.layout.activity_object_box, binding.navigationView, false);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) menuView.getLayoutParams();
        int statusBarHeight = UIUtils.getStatusBarHeight(this);
        layoutParams.topMargin = statusBarHeight;
        binding.navigationView.addHeaderView(menuView);
    }

    /**
     * 打开隐藏入口
     */
    private void openHide() {
        if ((System.currentTimeMillis() - mLastClickTime) <= 200) {
            if (mClickTimes < TOTAL_CLICK) {
                T.get(mContext).toast("还差" + (TOTAL_CLICK - mClickTimes) + "步打开隐藏功能");
                mClickTimes++;
            } else {
                T.get(mContext).toast("已经打开了");
            }
        }
        mLastClickTime = System.currentTimeMillis();
    }

    /**
     * 初始化手势监听
     */
    private void initGesture() {
        mGestureDetector = new GestureDetector(this);
        //添加默认手势序列
        mGestureTemplate = new ArrayList<>();
        mGestureTemplate.add(FLAG_HORIZENTAL);
        mGestureTemplate.add(FLAG_VERTICAL);
        mGestureTemplate.add(FLAG_VERTICAL);
        mGestureTemplate.add(FLAG_HORIZENTAL);
        mGestureTemplate.add(FLAG_HORIZENTAL);
        mGestureTemplate.add(FLAG_HORIZENTAL);
        mGestureTemplate.add(FLAG_VERTICAL);
        mGestureTemplate.add(FLAG_VERTICAL);
        mGestureTemplate.add(FLAG_VERTICAL);
        mGestureTemplate.add(FLAG_VERTICAL);

        mGestureList = new ArrayList<>();
    }

    @Override
    public boolean onDown(MotionEvent e) {
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
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getX() - e2.getX()) > minDistance) {
            if (mGestureList != null) {
                mGestureList.add(FLAG_HORIZENTAL);
            }
        } else if (Math.abs(e1.getY() - e2.getY()) > minDistance) {
            if (mGestureList != null) {
                mGestureList.add(FLAG_VERTICAL);
            }
        }
        checkGesture();
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 对比手势序列
     */
    private void checkGesture() {
        if (mGestureList != null && mGestureTemplate != null) {
            if (mGestureList.size() == mGestureTemplate.size()) {
                int matchCount = 0;
                for (int i = 0; i < mGestureList.size(); i++) {
                    if (!mGestureList.get(i).equals(mGestureTemplate.get(i))) {
                        break;
                    }
                    matchCount = i;
                }
                if (matchCount == mGestureList.size() - 1) {
                    T.get(mContext).toast("开启隐藏功能");
                }
                initGesture();
            }
        }
    }
}
