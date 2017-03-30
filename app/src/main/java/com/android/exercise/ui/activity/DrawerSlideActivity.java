package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.DisplayUtil;
import com.android.exercise.util.T;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawerSlideActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    @BindView(R.id.drawerContent)
    LinearLayout drawerContent;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerSlide)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private long mLastClickTime = 0;
    private int mClickTimes = 0;
    private int TOTAL_CLICK = 7;

    private GestureDetector mGestureDetector;
    private int minDistance = 300;//最小滑动距离
    private int FLAG_HORIZENTAL = 0;//横向
    private int FLAG_VERTICAL = 1;//纵向
    private ArrayList<Integer> mGestureTemplate;
    private ArrayList<Integer> mGestureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_slide);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        initActionbar();
        initDrawerLayout();
        initGesture();
    }

    private void initActionbar() {
        new ToolBarCommonHolder(this, mToolbar, getString(R.string.item_drawerslide), true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout != null) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                        mDrawerLayout.closeDrawer(Gravity.START);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.START);
                    }
                }
            }
        });
    }

    private void initDrawerLayout() {
        //去除阴影
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                int width = drawerView.getWidth();
                int left = (int) (slideOffset * width + 0.5f);
                int right = left + drawerContent.getWidth();
                drawerContent.layout(left, drawerContent.getTop(), right, drawerContent.getBottom());
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
        View menuView = getLayoutInflater().inflate(R.layout.activity_greendao, navigationView, false);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) menuView.getLayoutParams();
        int statusBarHeight = DisplayUtil.getStatusBarHeight(this);
        layoutParams.topMargin = statusBarHeight;
        navigationView.addHeaderView(menuView);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_drawerslide), true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout != null) {
                    if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                        mDrawerLayout.closeDrawer(Gravity.START);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.START);
                    }
                }
            }
        });
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @OnClick(R.id.btn_openHide)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_openHide:
                openHide();
                break;
        }
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
