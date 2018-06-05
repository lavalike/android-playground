package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.ui.widget.PageSwitchView;
import com.android.exercise.util.UIUtils;
import com.wangzhen.statusbar.DarkStatusBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PageSwitchActivity extends BaseActivity {

    @BindView(R.id.switch_view)
    PageSwitchView switchView;
    @BindView(R.id.iv_anim)
    ImageView ivAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_switch);
        ButterKnife.bind(this);
        DarkStatusBar.get().fitDark(this);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @OnClick(R.id.iv_anim)
    public void onViewClicked() {
        int[] centerXY = getCenterXYInScreen(ivAnim);
        int centerX = centerXY[0];
        int centerY = centerXY[1];
        switchView.setCenterXY(centerX, centerY);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        double distance = Math.sqrt(Math.pow(Math.abs(widthPixels - centerX), 2) + Math.pow(Math.abs(heightPixels - centerY), 2));
        switchView.setRadiusRange(0, (int) distance);
        switchView.start();
    }

    /**
     * 获取View中心点的屏幕坐标
     *
     * @param view
     * @return 坐标
     */
    public int[] getCenterXYInScreen(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        int centerX = array[0] + view.getMeasuredWidth() / 2;
        int centerY = array[1] + view.getMeasuredHeight() / 2;
        int statusBarHeight = UIUtils.getStatusBarHeight(this);
        return new int[]{centerX, centerY - statusBarHeight};
    }
}
