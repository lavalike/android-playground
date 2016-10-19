package com.android.exercise.common.toolbar;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.exercise.R;

/**
 * 常用布局 标题居中
 * Created by wangzhen on 16/10/19.
 */

public class CommonToolbar extends BaseToolBarHolder {
    TextView tv_title;

    public CommonToolbar(Activity mAct, Toolbar mToolbar, String title) {
        super(mAct, mToolbar);
        tv_title.setText(title);
    }

    @Override
    protected void initView() {
        tv_title = findById(R.id.tv_toolbar_title_id);
    }

    @Override
    protected int getToolBarLayoutResId() {
        return R.layout.layout_toolbar_common;
    }
}
