package com.android.exercise.ui.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import butterknife.ButterKnife;

/**
 * ConstraintLayoutActivity.java
 * Created by wangzhen on 2017/3/8.
 */
public class ConstraintLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        ButterKnife.bind(this);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_constraintlayout));
    }
}
