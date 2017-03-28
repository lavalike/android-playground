package com.android.exercise.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.util.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerSlideActivity extends BaseActivity {

    @BindView(R.id.drawerContent)
    LinearLayout drawerContent;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerSlide)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_slide);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        initActionbar();
        initDrawerLayout();
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
}
