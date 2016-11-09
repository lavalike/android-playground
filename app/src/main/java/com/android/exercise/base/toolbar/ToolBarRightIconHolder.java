package com.android.exercise.base.toolbar;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.exercise.R;

/**
 * 常用布局 ：左返回-标题中-右图标
 * Created by wangzhen on 16/10/19.
 */

public class ToolBarRightIconHolder extends BaseToolBarHolder {

    private TextView tv_title;
    private ImageView iv_menu;

    /**
     * @param activity
     * @param toolbar
     * @param title
     * @param canBack
     */
    public ToolBarRightIconHolder(Activity activity, Toolbar toolbar, String title, int resId, boolean canBack) {
        super(activity, toolbar);
        tv_title.setText(title);
        iv_menu.setImageResource(resId);
        if (!canBack) {
            //去除返回箭头
            mToolbar.setNavigationIcon(null);
            //去除TextView预留的右边距
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
            params.rightMargin = 0;
            tv_title.setLayoutParams(params);
        }
    }

    public ImageView getRightMenu() {
        return iv_menu;
    }

    @Override
    protected void initView() {
        tv_title = findById(R.id.tv_toolbar_right_icon_title);
        iv_menu = findById(R.id.iv_toolbar_right_icon_menu);
    }

    @Override
    protected int getToolBarLayoutResId() {
        return R.layout.layout_toolbar_right_icon;
    }

}
