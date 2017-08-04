package com.android.exercise.base.toolbar;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.exercise.R;

/**
 * 常用布局 ：左返回-标题中-右文字
 * Created by wangzhen on 16/10/19.
 */

public class ToolBarRightTextHolder extends BaseToolBarHolder {

    private TextView tv_title;
    private TextView tv_menu;

    public ToolBarRightTextHolder(Activity activity, Toolbar toolbar, String title, String menu) {
        this(activity, toolbar, title, menu, true);
    }

    /**
     * @param activity
     * @param toolbar
     * @param title
     * @param canBack
     */
    public ToolBarRightTextHolder(Activity activity, Toolbar toolbar, String title, String menu, boolean canBack) {
        super(activity, toolbar);
        tv_title.setText(title);
        tv_menu.setText(menu);
        if (!canBack) {
            //去除返回箭头
            mToolbar.setNavigationIcon(null);
            //去除TextView预留的右边距
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
            params.rightMargin = 0;
            tv_title.setLayoutParams(params);
        }
    }

    public TextView getRightMenu() {
        return tv_menu;
    }

    @Override
    protected void initView() {
        tv_title = findById(R.id.tv_toolbar_right_text_title);
        tv_menu = findById(R.id.tv_toolbar_right_text_menu);
    }

    @Override
    protected int getToolBarLayoutResId() {
        return R.layout.layout_toolbar_right_text;
    }

}
